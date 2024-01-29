package com.example.futsalowner


import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.futsalowner.MyForegroundServices.Companion.CHANNEL_ID
import com.google.firebase.database.*




class Dashboard : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var status: String
    private var id = 0


    // Firebase Database
    private lateinit var database: FirebaseDatabase
    private lateinit var bookingsRef: DatabaseReference

    @SuppressLint(
        "MissingInflatedId", "SuspiciousIndentation", "StringFormatInvalid",
        "ScheduleExactAlarm"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        //Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        bookingsRef = database.getReference("bookings")

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)

        // Clear previous data
        linearLayout.removeAllViews()
//        val serviceIntent = Intent(this, NotificationForegroundService::class.java)
//        ContextCompat.startForegroundService(this, serviceIntent)


        bookingsRef.addChildEventListener(object : ChildEventListener {

            @SuppressLint("SetTextI18n")
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // A new child has been added
                val buttonId = dataSnapshot.child("buttonId").value.toString()
                status = dataSnapshot.child("status").value.toString()
                name = dataSnapshot.child("name").value.toString()

                val textView = TextView(this@Dashboard)
                textView.text = "Booked for: $buttonId\nStatus: $status\nBooked by: $name\n\n"
                linearLayout.addView(textView)
                textView.tag = dataSnapshot.key
                id++
                //sendNotification()

                //startForegroundService(name,status,id)


            }


            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // Handle child node change if needed
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                // A child has been removed
                val key = dataSnapshot.key // Get the key of the removed child
                // Find and remove the corresponding TextView from the LinearLayout
                linearLayout.findViewWithTag<TextView>(key)?.let {
                    linearLayout.removeView(it)
                }
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // Handle child node move if needed
            }

            @SuppressLint("SetTextI18n")
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                val textView = TextView(this@Dashboard)
                textView.text = "Error: ${databaseError.message}"
                linearLayout.addView(textView)
            }
        })

//        val serviceIntent = Intent(this@Dashboard, MyForegroundServices::class.java)
//        startService(serviceIntent)


    }


//    createNotificationChannel()


//    private fun sendNotification() {
//        createNotificationChannel()
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.logo)
//            .setContentTitle(name)
//            .setContentText(status)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        with(NotificationManagerCompat.from(this)) {
//            if (ActivityCompat.checkSelfPermission(
//                    applicationContext,
//                    android.Manifest.permission.POST_NOTIFICATIONS//note to add android
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                return
//            }
//            notify(id, builder.build())
//        }
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID, "First Channel",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            channel.description = "Test description for my channel"
//
//            val notificationManager =
//                getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

//    private fun startForegroundService(name: String, status: String, id: Int) {
//        val serviceIntent = Intent(this, NotificationForegroundService::class.java).apply {
//            putExtra("name", name)
//            putExtra("status", status)
//            putExtra("id", id)
//        }
//        // Check if the service is already running
//        if (!isServiceRunning(NotificationForegroundService::class.java)) {
//            ContextCompat.startForegroundService(this, serviceIntent)
//        }
//    }
//
//    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
//        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
//            if (serviceClass.name == service.service.className) {
//                return true
//            }
//        }
//        return false
//    }

}






