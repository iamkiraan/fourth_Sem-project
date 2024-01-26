package com.example.futsalowner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.auth.FirebaseAuth

class signinActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    //private lateinit var auth: FirebaseAuth
    private lateinit var forgetPasswordText: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

//        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.LoginButton)
        forgetPasswordText = findViewById(R.id.forgetPassword)

        val registerText = findViewById<TextView>(R.id.register)


        registerText.setOnClickListener {
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }

//        forgetPasswordText.setOnClickListener {
//            val intent = Intent(this, ResetPasswordActivity::class.java)
//            startActivity(intent)
//        }

        loginButton.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()


            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (userEmail == "admin" || userPassword == "admin") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Email and password are invalid!!", Toast.LENGTH_SHORT).show()
            }

//            auth.signInWithEmailAndPassword(userEmail, userPassword)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                            finish()
//                        } else {
//                            Toast.makeText(baseContext, "User not found.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
        }

    }
}

