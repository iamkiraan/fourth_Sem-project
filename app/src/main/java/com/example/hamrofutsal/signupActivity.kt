package com.example.hamrofutsal

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class signupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var verifyOTP: Button
    private lateinit var PhoneNumber: EditText
    private lateinit var signupname: EditText
    private lateinit var signupemail: EditText
    private lateinit var signuppassword: EditText
    private lateinit var signupcpassword: EditText
    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = "Register"

        auth = FirebaseAuth.getInstance()

        verifyOTP = findViewById(R.id.VerifyOTP)
        PhoneNumber = findViewById(R.id.phoneNumber)
        signupname = findViewById(R.id.UserName)
        signupemail = findViewById(R.id.email)
        signuppassword = findViewById(R.id.password)
        signupcpassword = findViewById(R.id.Cpassword)

        verifyOTP.setOnClickListener {
            val name = signupname.text.toString()
            val number = PhoneNumber.text.toString()
            val email = signupemail.text.toString()
            val cpassword = signupcpassword.text.toString()
            val password = signuppassword.text.toString()

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)
            ) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show()
            } else if (password != cpassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (number.length < 10) {
                Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
            } else {
                // Phone number verification is initiated here
                startPhoneNumberVerification("+977$number",email,name)
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String,email:String,username:String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // This method will be called if the verification is completed automatically
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("signupActivity", "Verification failed: ${e.message}", e)
                    Toast.makeText(this@signupActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(
                    newVerificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // This method will be called when the verification code is successfully sent
                    this@signupActivity.verificationId = newVerificationId
                    Log.d("signupActivity", "Verification code sent to $phoneNumber")

                    val intent = Intent(this@signupActivity, OtpActivity::class.java)
                    intent.putExtra("verificationId", newVerificationId)
                    intent.putExtra("name",username)
                    intent.putExtra("email",email)
                    intent.putExtra("PhoneNumber", phoneNumber)
                    startActivity(intent)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}
