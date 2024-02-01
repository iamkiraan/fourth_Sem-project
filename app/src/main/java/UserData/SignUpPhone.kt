package UserData

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hamrofutsal.OtpActivity
import com.example.hamrofutsal.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SignUpPhone : AppCompatActivity() {


    private var verificationId: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var getOTP: Button
    private lateinit var phoneNumber: EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_phone)

        auth = FirebaseAuth.getInstance()

        getOTP = findViewById(R.id.getOTP)
        phoneNumber = findViewById(R.id.phoneNumber)

        getOTP.setOnClickListener {
            val number = phoneNumber.text.toString()

            if (TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please enter your number correctly", Toast.LENGTH_SHORT).show()
            } else {
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+977$number")
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            // This method will be called if the verification is completed automatically
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            Log.e("SignUpPhone", "Verification failed: ${e.message}", e)
                            Toast.makeText(
                                this@SignUpPhone,
                                "Verification failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onCodeSent(
                            newVerificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            // This method will be called when the verification code is successfully sent
                            this@SignUpPhone.verificationId = newVerificationId
                            Log.d("SignUpPhone", "Verification code sent to $number")

                            val intent = Intent(this@SignUpPhone, OtpActivity::class.java)
                            intent.putExtra("verificationId", newVerificationId)
                            intent.putExtra("PhoneNumber", number)
                            startActivity(intent)
                        }
                    })
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }
}
