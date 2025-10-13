package com.example.re

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FinalOrderActivity : AppCompatActivity() {

    private lateinit var productImageView: ImageView
    private lateinit var productDetailsTextView: TextView
    private lateinit var orderDetailsTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var productNameView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_order)

        productImageView = findViewById(R.id.productImage)
        productNameView = findViewById(R.id.productName)
        productDetailsTextView = findViewById(R.id.productDetails)
        orderDetailsTextView = findViewById(R.id.orderDetails)
        totalPriceTextView = findViewById(R.id.totalPrice)

        val name = intent.getStringExtra("NAME") ?: "No name"
        val email = intent.getStringExtra("EMAIL") ?: "No email"
        val address = intent.getStringExtra("ADDRESS") ?: "No address"
        val quantity = intent.getIntExtra("QUANTITY", 1)
        val width = intent.getDoubleExtra("WIDTH", 0.0)
        val height = intent.getDoubleExtra("HEIGHT", 0.0)
        val panelType = intent.getStringExtra("PANEL_TYPE") ?: "No type"
        val productImageId = intent.getIntExtra("PRODUCT_IMAGE", 0)
        val productName = intent.getStringExtra("PRODUCT_NAME") ?: "No product"
        val totalPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)

        productNameView.text = productName

        if (productImageId != 0) {
            productImageView.setImageResource(productImageId)
        }

        val orderDetails = """
            Name: $name
            Email: $email
            Address: $address
            Quantity: $quantity
            Size: ${width * height} sq.ft (Width x Height)
            Panel Type: $panelType
        """.trimIndent()

        orderDetailsTextView.text = orderDetails
        totalPriceTextView.text = "Total Price: ₹${totalPrice}"

        setupProceedToPaymentButton(totalPrice)
    }

    private fun setupProceedToPaymentButton(totalPrice: Double) {
        val proceedButton: Button = findViewById(R.id.proceedButton)
        proceedButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("ORDER_TOTAL", totalPrice)
            }
            startActivity(intent)
        }
    }
}

