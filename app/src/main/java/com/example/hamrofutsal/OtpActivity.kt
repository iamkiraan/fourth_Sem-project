package com.example.hamrofutsal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hamrofutsal.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText
    private lateinit var verifyOTP: Button
    private lateinit var resendText: TextView
    private lateinit var signupname: String
    private lateinit var signupemail: String
    private lateinit var PhoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resendText = findViewById(R.id.resend)
        inputCode1 = findViewById(R.id.InputCode1)
        inputCode2 = findViewById(R.id.InputCode2)
        inputCode3 = findViewById(R.id.InputCode3)
        inputCode4 = findViewById(R.id.InputCode4)
        inputCode5 = findViewById(R.id.InputCode5)
        inputCode6 = findViewById(R.id.InputCode6)
        setupOtpInput()
        verifyOTP = findViewById(R.id.Verify)

        // Retrieve data from the intent
        signupname = intent.getStringExtra("signup name") ?: ""
        signupemail = intent.getStringExtra("signup-email") ?: ""
        PhoneNumber = intent.getStringExtra("PhoneNumber") ?: ""

        verifyOTP.setOnClickListener {
            val otp = "${inputCode1.text}${inputCode2.text}${inputCode3.text}${inputCode4.text}${inputCode5.text}${inputCode6.text}"

            if (otp.length == 6) {
                // Verify the entered OTP
                val verificationId = intent.getStringExtra("verificationId") ?: ""
                val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this@OtpActivity, "Please enter the complete OTP", Toast.LENGTH_SHORT).show()
            }
        }

        resendText.setOnClickListener {
            val intent = Intent(this, ResendActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupOtpInput() {
        val editTextList = listOf(inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6)

        for (i in 0 until editTextList.size - 1) {
            editTextList[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        editTextList[i + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        inputCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Handle the last digit if needed
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("OtpActivity", "User authentication successful")
                    // Save user data to Firebase after successful authentication
                    saveUserToFirebase()
                    // Navigate to the desired activity
                    navigateToSignInActivity()
                } else {
                    Toast.makeText(this@OtpActivity, "Verification failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirebase() {
        Log.d("OtpActivity", "Saving user data to Firebase")
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            val usersRef = FirebaseDatabase.getInstance().getReference("users")
            val user = Users(
                name = signupname,
                phoneNumber = PhoneNumber,
                email = signupemail,
            )

            usersRef.child(uid).setValue(user)
        }
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(this, signinActivity::class.java)
        startActivity(intent)
        finish()
    }
}
