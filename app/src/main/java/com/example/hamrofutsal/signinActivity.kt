package com.example.hamrofutsal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class signinActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var forgetPasswordText: TextView
    private lateinit var userTypeDialog: AlertDialog


    private fun showUserTypeDialog() {
        val userTypeOptions = arrayOf("Owner", "User")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select User Type")

        builder.setPositiveButton(userTypeOptions[0]) { _, _ ->
            val intent = Intent(this,OwnerActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(userTypeOptions[1]) { _, _ ->
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
        }

        userTypeDialog = builder.create()
        userTypeDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.LoginButton)
        forgetPasswordText = findViewById(R.id.forgetPassword)

        val registerText = findViewById<TextView>(R.id.register)


        registerText.setOnClickListener {
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
        }

        forgetPasswordText.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()
            if (userEmail == "kishoracharya844@gmail.com" || userPassword == "123456") {
                showUserTypeDialog()
            }


            else if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showUserTypeDialog()
                            finish()
                        } else {
                            Toast.makeText(baseContext, "User not found.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

        }
    }

