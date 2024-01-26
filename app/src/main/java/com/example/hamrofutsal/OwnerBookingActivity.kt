package com.example.hamrofutsal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OwnerBookingActivity : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_booking)

        // Initialize Firebase Realtime Database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance().reference.child("bookings")

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

        buttons.forEach { button ->
            button.setOnClickListener { onButtonClick(button) }
        }

        // Set up a listener for changes in user bookings
        database.child("userBookings").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val bookingData = snapshot.getValue(BookingData::class.java)
                if (bookingData != null && bookingData.status == "Booked") {
                    onUserBookingAdded(bookingData)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val bookingData = snapshot.getValue(BookingData::class.java)
                if (bookingData != null) {
                    val buttonId = bookingData.buttonId.toIntOrNull()
                    if (buttonId != null && buttonId >= 0 && buttonId < buttons.size) {
                        when (bookingData.status) {
                            "Booked" -> {
                                // Handle the case where the booking status is changed to "Booked"
                                buttons[buttonId].visibility = View.VISIBLE
                            }
                            "Acknowledged" -> {
                                Toast.makeText(this@OwnerBookingActivity, "Booking acknowledged for button $buttonId", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }


            override fun onChildRemoved(snapshot: DataSnapshot) {
                // This function is called when a user booking is removed from the database

                val bookingData = snapshot.getValue(BookingData::class.java)
                val buttonId = bookingData?.buttonId?.toIntOrNull()

                if (buttonId != null && buttonId >= 0 && buttonId < buttons.size) {
                    // If the corresponding button exists, make it invisible again
                    buttons[buttonId].visibility = View.INVISIBLE
                }
            }


            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // aaile chaidaina
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database operation canceled: ${error.message}")

            }
        })
    }

    private fun onButtonClick(button: Button) {
        // Show dialog box with name and phone number
        showBookingDetailsDialog("User Name", "User Phone Number", buttons.indexOf(button))
    }

    private fun onUserBookingAdded(bookingData: BookingData) {
        // Update UI to make the corresponding button visible
        val buttonId = bookingData.buttonId.toIntOrNull()
        if (buttonId != null && buttonId >= 0 && buttonId < buttons.size) {
            buttons[buttonId].visibility = View.VISIBLE
        }
    }

    private fun showBookingDetailsDialog(userName: String, phoneNumber: String, buttonId: Int) {
        // Show a dialog with the user's name and phone number
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Booking Details")
            .setMessage("User Name: $userName\nPhone Number: $phoneNumber")
            .setPositiveButton("OK") { _, _ ->
                // Handle OK button click
                acknowledgeBooking(buttonId)
            }
            .setCancelable(false)
            .show()
    }

    private fun acknowledgeBooking(buttonId: Int) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val bookingId = buttonId.toString()


        val bookingReference = databaseReference.child("bookings").child(bookingId)

        // Update the status to "Acknowledged"
        bookingReference.child("status").setValue("Acknowledged")


        // Show a Toast message
        Toast.makeText(this, "Booking acknowledged for button $buttonId", Toast.LENGTH_SHORT).show()
    }
}

private fun Int.toIntOrNull(): Int {
    //
return 11
}
