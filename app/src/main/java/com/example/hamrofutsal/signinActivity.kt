package com.example.hamrofutsal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
class signinActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.LoginButton)

        val registerText = findViewById<TextView>(R.id.register)
        registerText.setOnClickListener {
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val semail = email.text.toString()
            val spassword = password.text.toString()

            if (semail.isEmpty() || spassword.isEmpty()) {
                if (semail.isEmpty()) {
                    email.error = "Enter your email"
                } else if (spassword.isEmpty()) {
                    password.error = "Enter your password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            } else {
                // Firebase authentication to sign in
                auth.signInWithEmailAndPassword(semail, spassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("signinActivity", "User authentication successful")
                            val intent = Intent(this@signinActivity,BookingData::class.java)
                            intent.putExtra("semail", semail)
                            startActivity(intent)
                            finish() // Close the current activity after successful sign-in
                        } else {
                            val exception = task.exception
                            Log.e("signinActivity", "Authentication failed", exception)
                            Toast.makeText(
                                this,
                                "Authentication failed. ${exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }
    }
}
