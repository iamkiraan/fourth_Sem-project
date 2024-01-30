package com.example.hamrofutsal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class UserSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_user_search)
    }
}