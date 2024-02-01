package com.example.hamrofutsal

import UserData.UserInformation
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hamrofutsal.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var inputCode1: EditText
    private lateinit var inputCode2: EditText
    private lateinit var inputCode3: EditText
    private lateinit var inputCode4: EditText
    private lateinit var inputCode5: EditText
    private lateinit var inputCode6: EditText
    private lateinit var verifyOTP: Button
    private lateinit var numberText : TextView
    private lateinit var resendText: TextView
    private lateinit var PhoneNumber: String
    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resendText = findViewById(R.id.resend)
        inputCode1 = findViewById(R.id.InputCode1)
        inputCode2 = findViewById(R.id.InputCode2)
        inputCode3 = findViewById(R.id.InputCode3)
        inputCode4 = findViewById(R.id.InputCode4)
        inputCode5 = findViewById(R.id.InputCode5)
        inputCode6 = findViewById(R.id.InputCode6)
        numberText = findViewById(R.id.textView)
        PhoneNumber = intent.getStringExtra("PhoneNumber") ?: ""
        numberText.text = PhoneNumber
        setupOtpInput()
        verifyOTP = findViewById(R.id.Verify)

        // Retrieve data from the intent
        PhoneNumber = intent.getStringExtra("PhoneNumber") ?: ""
        verificationId = intent.getStringExtra("verificationId") ?: ""

//        // Automatically fill OTP if received from Firebase
//        if (intent.hasExtra("autoFilledOtp")) {
//            val autoFilledOtp = intent.getStringExtra("autoFilledOtp")
//            if (autoFilledOtp != null) {
//                autoFillOtpFields(autoFilledOtp)
//            }
//        }

        verifyOTP.setOnClickListener {
            val otp = "${inputCode1.text}${inputCode2.text}${inputCode3.text}${inputCode4.text}${inputCode5.text}${inputCode6.text}"

            if (otp.length == 6) {
                // Verify the entered OTP
                val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this@OtpActivity, "Please enter the complete OTP", Toast.LENGTH_SHORT).show()
            }
        }

        resendText.setOnClickListener {
            // Call the function to resend OTP
            resendVerificationCode("+977$PhoneNumber")
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
                    // Navigate to the desired activity
                    navigateToUserInformation()
                } else {
                    Toast.makeText(this@OtpActivity, "Verification failed", Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun navigateToUserInformation() {
        val intent = Intent(this@OtpActivity, UserInformation::class.java)
        intent.putExtra("phoneNumber",PhoneNumber)
        startActivity(intent)
        finish()
    }

//    private fun autoFillOtpFields(autoFilledOtp: String) {
//        inputCode1.setText(autoFilledOtp[0].toString())
//        inputCode2.setText(autoFilledOtp[1].toString())
//        inputCode3.setText(autoFilledOtp[2].toString())
//        inputCode4.setText(autoFilledOtp[3].toString())
//        inputCode5.setText(autoFilledOtp[4].toString())
//        inputCode6.setText(autoFilledOtp[5].toString())
//    }

    private fun resendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // This method will be called if the verification is completed automatically
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("OtpActivity", "Verification failed", e)
                    Toast.makeText(this@OtpActivity, "Verification failed", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(
                    newVerificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(newVerificationId, token)
                    Log.d("OtpActivity", "Verification code sent")
                    Toast.makeText(this@OtpActivity, "Verification code sent", Toast.LENGTH_SHORT).show()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
