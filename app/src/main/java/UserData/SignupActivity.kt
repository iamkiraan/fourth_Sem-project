package com.example.hamrofutsal

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
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

    private var verificationId: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var verifyOTP: Button
    private lateinit var PhoneNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = "Register"

        auth = FirebaseAuth.getInstance()

        verifyOTP = findViewById(R.id.VerifyOTP)
        PhoneNumber = findViewById(R.id.phoneNumber)

        verifyOTP.setOnClickListener {
            val number = PhoneNumber.text.toString()

            if (TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please enter your number correctly", Toast.LENGTH_SHORT).show()
            } else {
                startPhoneNumberVerification("+977$number")
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
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
                    Toast.makeText(
                        this@signupActivity,
                        "Verification failed: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    intent.putExtra("PhoneNumber", phoneNumber)
                    startActivity(intent)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}


//import android.content.Intent
//import android.os.Bundle
//import android.text.TextUtils
//import android.util.Log
//import android.util.Patterns
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.FirebaseException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.PhoneAuthCredential
//import com.google.firebase.auth.PhoneAuthOptions
//import com.google.firebase.auth.PhoneAuthProvider
//import java.util.concurrent.TimeUnit
//
//class signupActivity : AppCompatActivity() {
//
//    private var verificationId: String
//    private lateinit var auth: FirebaseAuth
//    private lateinit var verifyOTP: Button
//    private lateinit var PhoneNumber: EditText
////    private lateinit var signupname: EditText
////    private lateinit var signupemail: EditText
////    private lateinit var signuppassword: EditText
////    private lateinit var signupcpassword: EditText
////    private lateinit var verificationId: String
////    private lateinit var loginText : TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        supportActionBar?.hide()
//        setContentView(R.layout.activity_signup)
//        supportActionBar?.title = "Register"
//
//        auth = FirebaseAuth.getInstance()
//
//        verifyOTP = findViewById(R.id.VerifyOTP)
//        PhoneNumber = findViewById(R.id.phoneNumber)
////        signupname = findViewById(R.id.UserName)
////        signupemail = findViewById(R.id.email)
////        signuppassword = findViewById(R.id.password)
////        signupcpassword = findViewById(R.id.Cpassword)
////        loginText = findViewById(R.id.login)
////        loginText.setOnClickListener{
////            val intent= Intent(this,signinActivity::class.java)
////            startActivity(intent)
////        }
//
//        verifyOTP.setOnClickListener {
////            val name = signupname.text.toString()
//            val number = PhoneNumber.text.toString()
////            val email = signupemail.text.toString()
////            val cpassword = signupcpassword.text.toString()
////            val password = signuppassword.text.toString()
//
//            if (TextUtils.isEmpty(number)) {
//                Toast.makeText(this, "please enter your number correclty", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                startPhoneNumberVerification(number)
//            }
//
//
////            ) {
////                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
////            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show()
////            } else if (password.length < 6) {
////                Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show()
////            } else if (password != cpassword) {
////                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
//
//
//        }
//
//
//    private fun startPhoneNumberVerification(PhoneNUmber: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    // This method will be called if the verification is completed automatically
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    Log.e("signupActivity", "Verification failed: ${e.message}", e)
//                    Toast.makeText(
//                        this@signupActivity,
//                        "Verification failed: ${e.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                override fun onCodeSent(
//                    newVerificationId: String,
//                    token: PhoneAuthProvider.ForceResendingToken
//                ) {
//                    // This method will be called when the verification code is successfully sent
//                    this@signupActivity.verificationId = newVerificationId
//                    Log.d("signupActivity", "Verification code sent to $phoneNumber")
//
//                    val intent = Intent(this@signupActivity, OtpActivity::class.java)
//                    intent.putExtra("verificationId", newVerificationId)
//                    intent.putExtra("PhoneNumber", phoneNumber)
//                    startActivity(intent)
//                }
//            })
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//    }
//}
