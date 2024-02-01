package UserData

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hamrofutsal.OtpActivity
import com.example.hamrofutsal.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var verifyOTP: Button
    private lateinit var PhoneNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)
        //supportActionBar?.title = "Register"

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
                    Log.e("SignupActivity", "Verification failed: ${e.message}", e)
                    Toast.makeText(
                        this@SignupActivity,
                        "Verification failed: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCodeSent(
                    newVerificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // This method will be called when the verification code is successfully sent
                    Log.d("SignupActivity", "Verification code sent to $phoneNumber")

                    val intent = Intent(this@SignupActivity, OtpActivity::class.java)
                    intent.putExtra("verificationId", newVerificationId)
                    intent.putExtra("PhoneNumber", phoneNumber)
                    startActivity(intent)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }


}
