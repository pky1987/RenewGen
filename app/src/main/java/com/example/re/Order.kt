package com.example.re

import android.content.Intent
import android.net.Uri
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
    private lateinit var widthEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var typeSpinner: Spinner
    private lateinit var submitButton: Button

    private lateinit var productName: String
    private var productImageRes: Int = 0
    private var productPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Initialize views
        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        addressEditText = findViewById(R.id.address)
        quantityEditText = findViewById(R.id.quantity)
        widthEditText = findViewById(R.id.width)
        heightEditText = findViewById(R.id.height)
        typeSpinner = findViewById(R.id.type)
        submitButton = findViewById(R.id.submitButton)

        // Get product details from Intent
        productName = intent.getStringExtra("PRODUCT_NAME") ?: "Unknown Product"
        productImageRes = intent.getIntExtra("PRODUCT_IMAGE", 0)
        productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)


        val panelTypes = arrayOf("Monocrystalline", "Polycrystalline", "Thin Film")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, panelTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = adapter

        submitButton.setOnClickListener {
            submitOrder()
        }
    }

    private fun submitOrder() {
        if (validateInputs()) {
            val panelType = typeSpinner.selectedItem.toString()
            val quantity = quantityEditText.text.toString().toDouble()
            val width = widthEditText.text.toString().toDouble()
            val height = heightEditText.text.toString().toDouble()

            val price = calculatePrice(quantity, width, height, panelType)

            val orderDetails = """
                Name: ${nameEditText.text}
                Email: ${emailEditText.text}
                Address: ${addressEditText.text}
                Quantity: $quantity
                Size: ${width * height} sq.ft (Width x Height)
                Panel Type: $panelType
                Estimated Delivery: ${getDeliveryDate()}
                Total Price: ₹$price
            """.trimIndent()

            val intent = Intent(this, FinalOrderActivity::class.java).apply {
                putExtra("NAME", nameEditText.text.toString())
                putExtra("EMAIL", emailEditText.text.toString())
                putExtra("ADDRESS", addressEditText.text.toString())
                putExtra("QUANTITY", quantity.toInt())
                putExtra("WIDTH", width)
                putExtra("HEIGHT", height)
                putExtra("PANEL_TYPE", panelType)
                putExtra("PRODUCT_IMAGE", productImageRes)
                putExtra("PRODUCT_NAME", productName) // Passing the product name here
                putExtra("PRODUCT_PRICE", price)
                putExtra("ORDER_DETAILS", orderDetails)
            }
            startActivity(intent)
        }
    }



    private fun calculatePrice(quantity: Double, width: Double, height: Double, panelType: String): Double {
        val area = width * height


        return when (panelType) {
            "Monocrystalline" -> quantity * area * 12.5
            "Polycrystalline" -> quantity * area * 10.0
            "Thin Film" -> quantity * area * 8.5
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
            quantityEditText.text.isEmpty() || quantityEditText.text.toString().toDouble() <= 0 -> {
                showError("Quantity must be greater than zero")
                false
            }
            widthEditText.text.isEmpty() || widthEditText.text.toString().toDouble() <= 0 -> {
                showError("Width must be greater than zero")
                false
            }
            heightEditText.text.isEmpty() || heightEditText.text.toString().toDouble() <= 0 -> {
                showError("Height must be greater than zero")
                false
            }
            else -> true
        }
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}
