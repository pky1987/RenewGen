package com.example.re

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Order : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var sizeEditText: EditText
    private lateinit var widthEditText: EditText
    private lateinit var typeSpinner: Spinner
    private lateinit var submitButton: Button
    private lateinit var orderSummaryTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        addressEditText = findViewById(R.id.address)
        quantityEditText = findViewById(R.id.quantity)
        sizeEditText = findViewById(R.id.size)
        widthEditText = findViewById(R.id.width)
        typeSpinner = findViewById(R.id.type)
        submitButton = findViewById(R.id.submitButton)
        orderSummaryTextView = findViewById(R.id.orderSummary)

        val panelTypes = arrayOf("Monocrystalline", "Polycrystalline", "Thin Film")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, panelTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = adapter

        submitButton.setOnClickListener {
            submitOrder()
        }
    }

    private fun submitOrder() {
        if(validateInputs()) {
            val panelType = typeSpinner.selectedItem.toString()
            val price = calculatePrice(
                quantity = quantityEditText.text.toString().toInt(),
                size = sizeEditText.text.toString().toDouble(),
                panelType = panelType
            )

            val orderDetails = """
                Name: ${nameEditText.text}
                Email: ${emailEditText.text}
                Address: ${addressEditText.text}
                Quantity: ${quantityEditText.text}
                Size: ${sizeEditText.text} sq.ft
                Panel Type: $panelType
                Estimated Delivery: ${getDeliveryDate()}
            """.trimIndent()

            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("ORDER_DETAILS", orderDetails)
                putExtra("ORDER_TOTAL", price)
            }
            startActivity(intent)
        }
    }

    private fun calculatePrice(quantity: Int, size: Double, panelType: String): Double {
        return when(panelType) {
            "Monocrystalline" -> quantity * size * 12.5
            "Polycrystalline" -> quantity * size * 10.0
            "Thin Film" -> quantity * size * 8.5
            else -> 0.0
        }
    }

    private fun getDeliveryDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.time)
    }

    private fun validateInputs(): Boolean {
        return when {
            nameEditText.text.isEmpty() -> {
                showError("Name is required")
                false
            }
            emailEditText.text.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailEditText.text).matches() -> {
                showError("Valid email is required")
                false
            }
            addressEditText.text.isEmpty() -> {
                showError("Address is required")
                false
            }
            quantityEditText.text.isEmpty() || quantityEditText.text.toString().toInt() <= 0 -> {
                showError("Quantity must be greater than zero")
                false
            }
            sizeEditText.text.isEmpty() || sizeEditText.text.toString().toDouble() <= 0 -> {
                showError("Size must be greater than zero")
                false
            }
            widthEditText.text.isEmpty() || widthEditText.text.toString().toDouble() <= 0 -> {
                showError("Width must be greater than zero")
                false
            }
            else -> true
        }
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}