package com.example.hamrofutsal


import UserMenu.UserProfileActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import futsalLocation.ImadolFutsal
import futsalLocation.JadibutiFutsal
import futsalLocation.KickFutsal
import futsalLocation.ManakamanaFutsal
import futsalLocation.NationalFutsal
import futsalLocation.PrimeFutsal

class UserDashboardActivity : AppCompatActivity() {
    private lateinit var buttonNav: BottomAppBar
    private lateinit var floatingPlus: FloatingActionButton
    private lateinit var navButton: BottomNavigationView
    private lateinit var futsalButton1: CardView
    private lateinit var futsalButton2: CardView
    private lateinit var futsalButton3: CardView
    private lateinit var futsalButton4: CardView
    private lateinit var futsalButton5: CardView
    private lateinit var futsalButton6: CardView
    private lateinit var btnLogout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_user_dashboard)

        buttonNav = findViewById(R.id.bottomAppBar)
        floatingPlus = findViewById(R.id.fab)
        navButton = findViewById(R.id.bottomNavigationView)
        futsalButton1 = findViewById(R.id.HamroFutsal1)
        futsalButton2 = findViewById(R.id.HamroFutsal2)
        futsalButton3 = findViewById(R.id.HamroFutsal3)
        futsalButton4 = findViewById(R.id.HamroFutsal4)
        futsalButton5 = findViewById(R.id.HamroFutsal5)
        futsalButton6 = findViewById(R.id.HamroFutsal6)

       // btnLogout =findViewById(R.id.btnLogout)

//        btnLogout.setOnClickListener {
//            logOut()
//        }




        navButton.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, UserHomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.Search -> {
                    val intent = Intent(this, UserSearchActivity::class.java)
                    startActivity(intent)
                }
                R.id.person -> {
                    val intent = Intent(this, UserProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.settings -> {
                    val intent = Intent(this, UserSettingActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        floatingPlus.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
        // clickebale button banauna lai
        val clickAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.click_animation)

        futsalButton1.setOnClickListener {

            it.startAnimation(clickAnimation)

            val intent = Intent(this, ImadolFutsal::class.java)
            startActivity(intent)
        }
        futsalButton2.setOnClickListener {

            it.startAnimation(clickAnimation)

            val intent = Intent(this, JadibutiFutsal::class.java)
            startActivity(intent)
        }
        futsalButton3.setOnClickListener {

            it.startAnimation(clickAnimation)

            val intent = Intent(this, KickFutsal::class.java)
            startActivity(intent)
        }
        futsalButton4.setOnClickListener {

            it.startAnimation(clickAnimation)

            val intent = Intent(this, ManakamanaFutsal::class.java)
            startActivity(intent)
        }
        futsalButton5.setOnClickListener{

            it.startAnimation(clickAnimation)

            val intent = Intent(this,NationalFutsal::class.java)
            startActivity(intent)
        }
        futsalButton6.setOnClickListener {

            it.startAnimation(clickAnimation)

            val intent = Intent(this, PrimeFutsal::class.java)
            startActivity(intent)
        }
    }
    // clickable button yeta samma ho 6 ota futsal ko lagi

//    private fun logOut() {
//        val signInIntent = Intent(this, signinActivity::class.java)
//        startActivity(signInIntent)
//        finish()
//    }
}

