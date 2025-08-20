package com.example.re

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        val orderDetails = intent.getStringExtra("ORDER_DETAILS") ?: ""
        val orderTotal = intent.getDoubleExtra("ORDER_TOTAL", 0.0)
        val orderId = "ORD-${System.currentTimeMillis()}"

        findViewById<TextView>(R.id.orderId).text = "Order ID: $orderId"
        findViewById<TextView>(R.id.orderDetails).text = """
            $orderDetails
            Total Amount: ₹${"%.2f".format(orderTotal)}
        """.trimIndent()

        findViewById<Button>(R.id.trackOrderButton).setOnClickListener {
            startActivity(Intent(this, OrderTrackingActivity::class.java).apply {
                putExtra("ORDER_ID", orderId)
            })
        }

        findViewById<Button>(R.id.continueShoppingButton).setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }

        sendConfirmationEmail(orderId, orderTotal)
    }

    private fun sendConfirmationEmail(orderId: String, amount: Double) {
        // Implement email sending logic here
        Toast.makeText(this, "Confirmation email sent", Toast.LENGTH_SHORT).show()
    }
}