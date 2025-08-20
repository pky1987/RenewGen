package com.example.re

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import org.json.JSONArray

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message") ?: ""

        val sharedPref = context.getSharedPreferences("Notifications", Context.MODE_PRIVATE)
        val messagesArray = JSONArray(sharedPref.getString("messages", "[]")).apply {
            put(message)
        }
        sharedPref.edit().putString("messages", messagesArray.toString()).apply()

        val nextIntent = Intent(context, NotificationMessagesActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            PendingIntent.getActivity(
                context,
                0,
                nextIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )?.let { pendingIntent ->
                NotificationManagerCompat.from(context).notify(
                    System.currentTimeMillis().toInt(),
                    NotificationCompat.Builder(context, HomeFragment.CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Scheduled Notification")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()
                )
            }
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, NotificationReceiver::class.java).apply {
                putExtra("message", "Together, we can power the world with renewables.")
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        ).also { pendingIntent ->
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 300000,
                pendingIntent
            )
        }
    }
}