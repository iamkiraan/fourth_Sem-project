package com.example.hamrofutsal

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Timestamp(
    val startTime: Long = 0,
    val endTime: Long = 0,
    val ownerId: String = ""
) {
    var isHold: Boolean = false
        get() {
            // TODO: Implement logic for isHold
            return false
        }
    var isBooked: Boolean = false
        get() {
            // TODO: Implement logic for isBooked
            return false
        }
}

class OwnerActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var selectedStartTime: Long = 0
    private var selectedEndTime: Long = 0

    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "1212"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        mAuth = FirebaseAuth.getInstance()

        createNotificationChannel()

        val buttonCreateTimestamp: Button = findViewById(R.id.buttonCreateTimestamp)
        val startButton = findViewById<Button>(R.id.buttonSelectStartTime)
        val endButton = findViewById<Button>(R.id.buttonSelectEndTime)
        val deleteButton = findViewById<Button>(R.id.buttonDeleteTimeStamp)
        val startText = findViewById<TextView>(R.id.textViewStartTime)
        val endText = findViewById<TextView>(R.id.textViewEndTime)

        startButton.visibility = View.GONE
        endButton.visibility = View.GONE
        deleteButton.visibility = View.GONE
        startText.visibility = View.GONE

        buttonCreateTimestamp.setOnClickListener {
            startButton.visibility = View.VISIBLE
            endButton.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            startText.visibility = View.VISIBLE
            createTimestamp()
        }

        startButton.setOnClickListener {
            // Get the current time as the default time for the TimePickerDialog
            val currentTimeMillis = System.currentTimeMillis()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = currentTimeMillis
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            // Create a TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    // Handle the selected start time
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedStartTime = calendar.timeInMillis

                    // Update the UI to display the selected start time
                    startText.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

                },
                currentHour,
                currentMinute,
                false
            )

            timePickerDialog.show()
        }

        endButton.setOnClickListener {
            val currentTimeMillis = System.currentTimeMillis()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = currentTimeMillis
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->

                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedEndTime = calendar.timeInMillis

                    endText.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

                },
                currentHour,
                currentMinute,
                false
            )

            timePickerDialog.show()
        }
    }

    private fun createTimestamp() {
        if (selectedStartTime == 0L || selectedEndTime == 0L || selectedEndTime <= selectedStartTime) {
            // Handle case where start or end time is not selected or end time is not valid
            return
        }

        val ownerId = mAuth.currentUser?.uid ?: ""

        val timestamp = Timestamp(selectedStartTime, selectedEndTime, ownerId)

        val database = FirebaseDatabase.getInstance()
        val timestampRef = database.getReference("timestamps")

        timestampRef.push().setValue(timestamp)
            .addOnSuccessListener {
                // Timestamp created successfully
                // Notify users or perform other actions
                showNotification("Timestamp Created", "Timestamp created successfully.")
            }
            .addOnFailureListener {
                // Handle failure
                showNotification("Timestamp Creation Failed", "Failed to create timestamp.")
            }
    }

    private fun showNotification(title: String, message: String) {
        // Create an explicit intent for the MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // Show the notification
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@OwnerActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Handle permissions
                return
            }
            notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Your Channel Name"
            val descriptionText = "Your channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
