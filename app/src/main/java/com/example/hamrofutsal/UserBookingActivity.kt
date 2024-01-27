package com.example.hamrofutsal

import android.content.Intent
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
    private var status = ""

    private lateinit var database: FirebaseDatabase
    private lateinit var bookingsRef: DatabaseReference

    private var isButtonBooked = false
    private var bookedButtonId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_booking)

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
        if (isButtonBooked) {
            showDeletePreviousBookingDialog(button)
        } else {
            selectedButtonId = button.id

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Book or Hold?")
                .setPositiveButton("Book") { _, _ ->
                    bookButton(button)
                }
                .setNegativeButton("Hold") { _, _ ->
                    holdButton(button)
                }
                .setNeutralButton("Back") { _, _ ->
                    backButton(button)
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun showDeletePreviousBookingDialog(newButton: Button) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("You have already booked a slot.")
            .setMessage("Do you want to delete the previous booking?")
            .setPositiveButton("Delete") { _, _ ->
                deletePreviousBooking(newButton)
            }
            .setNegativeButton("Cancel") { _, _ ->
               showToast("you cancelled booking")
            }
            .setCancelable(false)
            .show()
    }

    private fun deletePreviousBooking(newButton: Button) {
        if (bookedButtonId != -1) {
            removeBookingFromDatabase(bookedButtonId)
            val previousButton = buttons.find { it.id == bookedButtonId }
            restoreButtonState(previousButton)
            showToast("Previous booking deleted.")
            bookButton(newButton)
        }
    }

    private fun removeBookingFromDatabase(buttonId: Int) {
        bookingsRef.child(buttonId.toString()).removeValue()
    }

    private fun bookButton(button: Button) {
        if (originalTextMap.containsKey(button) && originalTextMap[button]?.contains("Booked") == true) {
            showToast("Already booked slot. Please choose another time for booking.")
            return
        }

        originalTextMap[button] = button.text.toString()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        button.text = "Booked ($currentTime)"
        button.isEnabled = false
        status = "Booked"
        isButtonBooked = true
        bookedButtonId = selectedButtonId

        showToast("Time booked!")

        saveBookingToDatabase(selectedButtonId)

        handler.postDelayed({
            restoreButtonState(button)
            showToast("Time slot available again")
            isButtonBooked = false
        }, 3600000)
    }

    private fun holdButton(button: Button) {
        originalTextMap[button] = button.text.toString()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        button.text = "Hold ($currentTime)"
        button.isEnabled = false
        status = "Hold"
        showToast("Time on hold for 30 seconds")

        handler.postDelayed({
            restoreButtonState(button)
        }, 30000)
    }

    private fun backButton(button: Button) {
        val intent = Intent(this@UserBookingActivity, UserBookingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun restoreButtonState(button: Button?) {
        button?.let {
            it.text = originalTextMap[it]
            it.isEnabled = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveBookingToDatabase(buttonId: Int) {
        val name = intent.getStringExtra("semail") ?: ""
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
