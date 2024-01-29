package com.example.hamrofutsal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //private lateinit var message: TextView
    private lateinit var futsalName: TextView
    private lateinit var logo: ImageView
    private lateinit var  topView1 : View
    private lateinit var  topView2 : View
    private lateinit var  topView3 : View
    private lateinit var  topView4 : View
    private lateinit var  topView5 : View
    private lateinit var  topView6 : View
    private lateinit var Button : Button
    private var count: Int = 0



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView: View = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        setContentView(R.layout.activity_main)
        //message = findViewById(R.id.message)
        futsalName = findViewById(R.id.futsalNamee)
        logo = findViewById(R.id.Logo)
        topView1= findViewById(R.id.topView1)
        topView2= findViewById(R.id.topView2)
        topView3= findViewById(R.id.topView3)
        topView4= findViewById(R.id.topView4)
        topView5= findViewById(R.id.topView5)
        topView6= findViewById(R.id.topView6)
        Button = findViewById(R.id.Button)
        Button.setOnClickListener {

          val intent = Intent(this,signinActivity::class.java)
            startActivity(intent)

        }

        val logoAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation)
        //val messageAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.hold_animation)
        val text1Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_views_animation)
        val text2Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_views_animation)
        val text3Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_views_animation)
        val text4Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_view_animation)
        val text5Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_view_animation)
        val text6Animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_view_animation)
        val fustalAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation)
        val ButtonAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation)
        topView1.startAnimation(text1Animation)
        topView6.startAnimation(text6Animation)
        text1Animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                topView2.visibility = View.VISIBLE
                topView5.visibility = View.VISIBLE
                topView2.startAnimation(text2Animation)
                topView5.startAnimation(text5Animation)
            }

            override fun onAnimationRepeat(animation: Animation?) {


            }
        })
        text2Animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                topView3.visibility = View.VISIBLE
                topView4.visibility = View.VISIBLE
                topView3.startAnimation(text3Animation)
                topView4.startAnimation(text4Animation)
            }

            override fun onAnimationRepeat(animation: Animation?) {


            }
        })
        text3Animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                logo.visibility = View.VISIBLE
                logo.startAnimation(logoAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {


            }
        })
        logoAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                futsalName.visibility = View.VISIBLE
                futsalName.startAnimation(fustalAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {


            }
        })
        fustalAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                Button.visibility = View.VISIBLE
                Button.startAnimation(ButtonAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {


            }
        })


//        fustalAnimation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                message.visibility = View.VISIBLE
//                val animateText = message.text.toString()
//                message.text = ""
//                val duration = animateText.length * 120L
//
//                val countDownTimer = object : CountDownTimer(duration, 15) {
//                    override fun onTick(millisUntilFinished: Long) {
//                        if (count < animateText.length) {
//                            message.text = "${message.text}${animateText[count]}"
//                            count++
//                            Button.visibility = View.VISIBLE
//                            Button.startAnimation(ButtonAnimation)
//
//                        }
//
//                    }
//
//
//                    override fun onFinish() {
//
//
//                    }
//                }
//
//                countDownTimer.start()
//
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//
//
//            }
//        })


    }
}