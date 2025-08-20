package com.example.re

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Date

class NotificationMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_messages)

        val sharedPref = getSharedPreferences("Notifications", MODE_PRIVATE)
        val messagesArray = JSONArray(sharedPref.getString("messages", "[]"))
        val currentTime = System.currentTimeMillis()

        // Migrate old messages to new format
        val updatedArray = JSONArray()
        for (i in 0 until messagesArray.length()) {
            try {
                val item = messagesArray[i]
                if (item is String) {
                    // Convert old string format to new JSONObject format
                    updatedArray.put(JSONObject().apply {
                        put("text", item)
                        put("timestamp", currentTime)
                        put("removeTime", JSONObject.NULL)
                    })
                } else {
                    updatedArray.put(item)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        // Save migrated data back
        sharedPref.edit().putString("messages", updatedArray.toString()).apply()

        val messages = mutableListOf<JSONObject>()
        for (i in 0 until updatedArray.length()) {
            try {
                val msgObj = updatedArray.getJSONObject(i)
                val removeTime = msgObj.optLong("removeTime", -1)
                if (removeTime == -1L || currentTime < removeTime) {
                    messages.add(msgObj)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        // Rest of the code remains the same...
        val adapter = object : ArrayAdapter<JSONObject>(this, R.layout.item_message, messages) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.item_message, parent, false)
                val messageObj = getItem(position)!!
                val dateText = view.findViewById<TextView>(R.id.dateText)
                val messageText = view.findViewById<TextView>(R.id.messageText)

                val timestamp = messageObj.getLong("timestamp")
                val date = Date(timestamp)
                val dateStr = android.text.format.DateFormat.format("dd/MM/yyyy HH:mm", date).toString()

                dateText.text = dateStr
                messageText.text = messageObj.getString("text")

                return view
            }
        }

        findViewById<ListView>(R.id.messagesListView).apply {
            this.adapter = adapter
            setOnItemClickListener { _, _, position, _ ->
                val messageObj = messages[position]
                messageObj.put("removeTime", System.currentTimeMillis() + 3600000)

                val updatedMessagesArray = JSONArray()
                for (i in 0 until updatedArray.length()) {
                    val obj = updatedArray.getJSONObject(i)
                    if (obj.getLong("timestamp") == messageObj.getLong("timestamp")) {
                        updatedMessagesArray.put(messageObj)
                    } else {
                        updatedMessagesArray.put(obj)
                    }
                }
                sharedPref.edit().putString("messages", updatedMessagesArray.toString()).apply()

                Handler().postDelayed({
                    val currentTime = System.currentTimeMillis()
                    if (currentTime >= messageObj.getLong("removeTime")) {
                        messages.removeAt(position)
                        adapter.notifyDataSetChanged()

                        val newArray = JSONArray()
                        messages.forEach { newArray.put(it) }
                        sharedPref.edit().putString("messages", newArray.toString()).apply()
                    }
                }, 3600000)
            }
        }
    }
}