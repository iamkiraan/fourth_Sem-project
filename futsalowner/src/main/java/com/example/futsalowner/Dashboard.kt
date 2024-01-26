package com.example.futsalowner

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Dashboard : AppCompatActivity() {

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView


    // Firebase Database
    private lateinit var database: FirebaseDatabase
    private lateinit var bookingsRef: DatabaseReference

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        bookingsRef = database.getReference("bookings")

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)
        textView5 = findViewById(R.id.textView5)

//        val btnId = "buttonId"
//        val buttonId = btnId.toString();

        bookingsRef.child("buttonId").get().addOnSuccessListener {
            if (it.exists()){
                val bookingTime = it.child("buttonId").value
                val email = it.child("email").value
                val location = it.child("phone").value
                Toast.makeText(this,"Results Found",Toast.LENGTH_SHORT).show()
                textView1.text = bookingTime.toString()

            }else{
                Toast.makeText(this,"Phone number does not exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    }

}



