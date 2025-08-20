package com.example.re

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class OrderTrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_tracking)

        val orderId = intent.getStringExtra("ORDER_ID") ?: return
        // Simulate fetching order status
        val orderStatus = getOrderStatus(orderId)

        findViewById<TextView>(R.id.orderIdTextView).text = "Order ID: $orderId"
        findViewById<TextView>(R.id.orderStatusTextView).text = "Status: $orderStatus"
    }

    private fun getOrderStatus(orderId: String): String {
        // Simulate fetching order status from a server
        return when (Random().nextInt(3)) {
            0 -> "Processing"
            1 -> "Shipped"
            else -> "Delivered"
        }
    }
}