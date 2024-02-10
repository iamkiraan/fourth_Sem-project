package com.example.futsalowner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ResendActivity : AppCompatActivity() {
    private lateinit var ResendButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resend)
        ResendButton = findViewById(R.id.Resend)
//        ResendButton.setOnClickListener{
//            val intent = Intent(this,OtpActivity::class.java)
//            startActivity(intent)
//        }

    }
}