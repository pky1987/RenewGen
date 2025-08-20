package com.example.re

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentActivity : AppCompatActivity() {
    private lateinit var paymentMethodSpinner: Spinner
    private lateinit var upiPaymentLayout: LinearLayout
    private lateinit var cardPaymentLayout: LinearLayout
    private lateinit var qrPaymentLayout: LinearLayout
    private lateinit var transactionDao: TransactionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner)
        upiPaymentLayout = findViewById(R.id.upiPaymentLayout)
        cardPaymentLayout = findViewById(R.id.cardPaymentLayout)
        qrPaymentLayout = findViewById(R.id.qrPaymentLayout)
        transactionDao = PaymentDatabase.getDatabase(this)
        setupPaymentMethodSpinner()
    }

    private fun setupPaymentMethodSpinner() {
        val adapter = ArrayAdapter.createFromResource(this,R.array.payment_methods,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentMethodSpinner.adapter = adapter
        paymentMethodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        upiPaymentLayout.visibility = View.VISIBLE
                        cardPaymentLayout.visibility = View.GONE
                        qrPaymentLayout.visibility = View.GONE
                    }
                    1 -> {
                        upiPaymentLayout.visibility = View.GONE
                        cardPaymentLayout.visibility = View.VISIBLE
                        qrPaymentLayout.visibility = View.GONE
                    }
                    2 -> {
                        upiPaymentLayout.visibility = View.GONE
                        cardPaymentLayout.visibility = View.GONE
                        qrPaymentLayout.visibility = View.VISIBLE
                    }
                }
                setupPayButtons()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                upiPaymentLayout.visibility = View.GONE
                cardPaymentLayout.visibility = View.GONE
                qrPaymentLayout.visibility = View.GONE
            }
        }
    }

    private fun setupPayButtons() {
        val upiPayButton: Button = upiPaymentLayout.findViewById(R.id.upiPayButton)
        upiPayButton.setOnClickListener {
            val upiIdTextView: TextView = upiPaymentLayout.findViewById(R.id.upiIdTextView)
            val upiId = upiIdTextView.text.toString()
            val amountText = upiPaymentLayout.findViewById<EditText>(R.id.upiAmountEditText).text.toString()
            if (upiId.isNotEmpty() && amountText.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull()
                if (amount != null && amount > 0) initiateUpiPayment(upiId, amount)
            }
        }
        val cardPayButton: Button = cardPaymentLayout.findViewById(R.id.cardPayButton)
        cardPayButton.setOnClickListener {
            val cardNumber = cardPaymentLayout.findViewById<EditText>(R.id.cardNumberEditText).text.toString()
            val expiry = cardPaymentLayout.findViewById<EditText>(R.id.cardExpiryEditText).text.toString()
            val cvv = cardPaymentLayout.findViewById<EditText>(R.id.cardCvvEditText).text.toString()
            val amountText = cardPaymentLayout.findViewById<EditText>(R.id.cardAmountEditText).text.toString()
            if (amountText.isNotEmpty()) {
                val amount = amountText.toDouble()
                val details = "Card: $cardNumber, Exp: $expiry, CVV: $cvv"
                saveTransaction("Card", amount, details)
            }
        }
    }

    private fun initiateUpiPayment(upiId: String, amount: Double) {
        val uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", "Merchant Name")
            .appendQueryParameter("am", amount.toString())
            .appendQueryParameter("cu", "INR")
            .build()
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        startActivityForResult(intent, UPI_PAYMENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPI_PAYMENT_REQUEST_CODE) {
            val response = data?.getStringExtra("response") ?: "No response"
            handleUpiPaymentResponse(response)
        }
    }

    private fun handleUpiPaymentResponse(response: String) {
        if (response.contains("SUCCESS")) {
            val amount = findViewById<EditText>(R.id.upiAmountEditText).text.toString().toDoubleOrNull()
            amount?.let { saveTransaction("UPI", it, "Payment successful via UPI") }
        }
    }

    private fun saveTransaction(method: String, amount: Double, details: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            transactionDao.insert(Transaction(method, amount, details))
        }
    }

    companion object {
        private const val UPI_PAYMENT_REQUEST_CODE = 1
    }
}