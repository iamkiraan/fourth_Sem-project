package com.example.futsalowner

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class signupActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth
//    private lateinit var verifyOTP: Button
//    private lateinit var PhoneNumber: EditText
//    private lateinit var signupname: EditText
//    private lateinit var signupemail: EditText
//    private lateinit var signuppassword: EditText
//    private lateinit var signupcpassword: EditText
//    private lateinit var radioGroup: RadioGroup
//    private lateinit var ownerRadio: RadioButton
//    private lateinit var userRadio: RadioButton
//    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = "Register"
//
//        auth = FirebaseAuth.getInstance()
//
//        verifyOTP = findViewById(R.id.VerifyOTP)
//        PhoneNumber = findViewById(R.id.phoneNumber)
//        signupname = findViewById(R.id.UserName)
//        signupemail = findViewById(R.id.email)
//        signuppassword = findViewById(R.id.password)
//        signupcpassword = findViewById(R.id.Cpassword)
//        radioGroup = findViewById(R.id.RadioGroup)
//        ownerRadio = findViewById(R.id.ownerRadioButton)
//        userRadio = findViewById(R.id.userRadioButton)
//
//        verifyOTP.setOnClickListener {
//            val name = signupname.text.toString()
//            val number = PhoneNumber.text.toString()
//            val email = signupemail.text.toString()
//            val cpassword = signupcpassword.text.toString()
//            val password = signuppassword.text.toString()
//
//            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number) || TextUtils.isEmpty(email) ||
//                TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)
//            ) {
//                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
//            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
//            } else if (password.length < 6) {
//                Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show()
//            } else if (password != cpassword) {
//                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
//            } else if (number.length < 10) {
//                Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
//            } else {
//               //phone number verification hunxa yeta
//                startPhoneNumberVerification("+977$number")
//            }
//        }
//    }
//
//    private fun startPhoneNumberVerification(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    //yeta kunai specific kaam garnu pardaina aaile laii
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    Toast.makeText(this@signupActivity, "Verification failed", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onCodeSent(
//                    verificationId: String,
//                    token: PhoneAuthProvider.ForceResendingToken
//                ) {
//                    this@signupActivity.verificationId = verificationId
//
//                    val intent = Intent(this@signupActivity, OtpActivity::class.java)
//                    intent.putExtra("verificationId", verificationId)
//                    startActivity(intent)
//                }
//            })
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
    }
}
