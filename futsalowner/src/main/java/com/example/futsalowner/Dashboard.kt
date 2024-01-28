package com.example.futsalowner

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar


const val CHANNEL_ID = "channelId"
const val NOTIFICATION_ID = 0
class Dashboard : AppCompatActivity() {



    // Firebase Database
    private lateinit var database: FirebaseDatabase
    private lateinit var bookingsRef: DatabaseReference

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "StringFormatInvalid",
        "ScheduleExactAlarm"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        // Initialize Firebase Database
//        database = FirebaseDatabase.getInstance()
//        bookingsRef = database.getReference("bookings")
//
//        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)
//
//        // Clear previous data
//        linearLayout.removeAllViews()
//
//        bookingsRef.addChildEventListener(object : ChildEventListener {
//
//            @SuppressLint("SetTextI18n")
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                // A new child has been added
//                val buttonId = dataSnapshot.child("buttonId").value.toString()
//                val status = dataSnapshot.child("status").value.toString()
//                val name = dataSnapshot.child("name").value.toString()
//
//                val textView = TextView(this@Dashboard)
//                textView.text = "Booked for: $buttonId\nStatus: $status\nBooked by: $name\n\n"
//                linearLayout.addView(textView)
//                textView.tag = dataSnapshot.key // Store the key as a tag for later reference
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                // Handle child node change if needed
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                // A child has been removed
//                val key = dataSnapshot.key // Get the key of the removed child
//                // Find and remove the corresponding TextView from the LinearLayout
//                linearLayout.findViewWithTag<TextView>(key)?.let {
//                    linearLayout.removeView(it)
//                }
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                // Handle child node move if needed
//            }
//
//            @SuppressLint("SetTextI18n")
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle errors
//                val textView = TextView(this@Dashboard)
//                textView.text = "Error: ${databaseError.message}"
//                linearLayout.addView(textView)
//            }
//        })


        createNotificationChannel()
        scheduleNotification()


    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel Description"
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            // Set the desired time for the notification (e.g., 10:00 AM)
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 40)
            set(Calendar.SECOND, 40)
        }

        // Set alarm to trigger at the specified time
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Display notification
        context?.let {
            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("Notification text")
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}





