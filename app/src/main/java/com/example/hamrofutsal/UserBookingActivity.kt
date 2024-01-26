package com.example.hamrofutsal

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserBookingActivity : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private val handler = Handler(Looper.getMainLooper())
    private val originalTextMap = mutableMapOf<Button, String>()
    private var selectedButtonId: Int = -1
    // Change to String type
    private var status = ""

    // Firebase Database
    private lateinit var database: FirebaseDatabase
    private lateinit var bookingsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_booking)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        bookingsRef = database.reference.child("bookings")

        buttons = listOf(
            findViewById(R.id.btn7to8),
            findViewById(R.id.btn8to9),
            findViewById(R.id.btn9to10),
            findViewById(R.id.btn10to11),
            findViewById(R.id.btn11to12),
            findViewById(R.id.btn12to1),
            findViewById(R.id.btn1to2),
            findViewById(R.id.btn2to3),
            findViewById(R.id.btn3to4),
            findViewById(R.id.btn4to5),
            findViewById(R.id.btn5to6),
            findViewById(R.id.btn6to7)
        )

        for (button in buttons) {
            button.setOnClickListener { onButtonClick(button) }
        }
    }

    private fun onButtonClick(button: Button) {
        // Store the ID of the clicked button as a string
        selectedButtonId = button.id

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Book or Hold?")
            .setPositiveButton("Book") { _, _ ->
                bookButton(button)
            }
            .setNegativeButton("Hold") { _, _ ->
                holdButton(button)
            }
            .setCancelable(false)
            .show()
    }

    private fun bookButton(button: Button) {
        originalTextMap[button] = button.text.toString()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        button.text = "Booked ($currentTime)"
        button.isEnabled = false
        status = "Booked"

        showToast("Button booked!")

        // Save booking data to the database
        saveBookingToDatabase(selectedButtonId)

        handler.postDelayed({
            restoreButtonState(button)
            showToast("Button available again")
        }, 3600000)
    }

    private fun holdButton(button: Button) {
        originalTextMap[button] = button.text.toString()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        button.text = "Hold ($currentTime)"
        button.isEnabled = false
        status = "Hold"
        showToast("Button on hold for 30 seconds")

        handler.postDelayed({
            restoreButtonState(button)
        }, 30000)
    }

    private fun restoreButtonState(button: Button) {
        button.text = originalTextMap[button]
        button.isEnabled = true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveBookingToDatabase(buttonId : Int) {
        // Get user information (You need to implement this part)
        val name = intent.getStringExtra("semail") ?: ""

        // Perform actions to acknowledge the booking in the database
        // For simplicity, let's assume you have a "bookings" node in the database
        // and you want to update the status to "Acknowledged".

        // val status = "Booked" // Set the appropriate status
        val bookingData = BookingData(name, buttonId.toInt(), status)

        bookingsRef.child(buttonId.toString()).setValue(bookingData)
            .addOnSuccessListener {
                showToast("Booking acknowledged for button $buttonId")
            }
            .addOnFailureListener {
                showToast("Failed to acknowledge booking for button $buttonId")
            }
    }
}
