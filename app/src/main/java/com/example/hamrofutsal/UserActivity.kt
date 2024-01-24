package com.example.hamrofutsal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var timestampsRef: DatabaseReference
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        timestampsRef = database.getReference("timestamps")

        retrieveTimestamps()

    }

    private fun retrieveTimestamps() {
        timestampsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val timestampsList = mutableListOf<Timestamp>()

                for (childSnapshot in snapshot.children) {
                    val timestamp = childSnapshot.getValue(Timestamp::class.java)
                    if (timestamp != null) {
                        timestampsList.add(timestamp)
                    }
                }

                displayButtons(timestampsList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun displayButtons(timestampsList: List<Timestamp>) {
        linearLayout.removeAllViews()

        for (timestamp in timestampsList) {
            val button = Button(this)
            button.text = formatTime(timestamp.startTime, timestamp.endTime)
            button.setOnClickListener {
                showOptionsDialog(timestamp)
            }

            if (timestamp.isBooked) {
                button.visibility = View.INVISIBLE
            } else if (timestamp.isHold) {
                button.text = "Hold"
            }

            linearLayout.addView(button)
        }
    }

    private fun formatTime(startTime: Long, endTime: Long): String {
        return "$startTime - $endTime"
    }

    private fun showOptionsDialog(timestamp: Timestamp) {
        val options = arrayOf("Book", "Hold")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> bookTimestamp(timestamp)
                    1 -> holdTimestamp(timestamp)
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun bookTimestamp(timestamp: Timestamp) {
        timestamp.isBooked = true
        timestampsRef.child(timestamp.startTime.toString()).setValue(timestamp)
    }

    private fun holdTimestamp(timestamp: Timestamp) {
        timestamp.isHold = true
        timestampsRef.child(timestamp.startTime.toString()).setValue(timestamp)
    }
}
