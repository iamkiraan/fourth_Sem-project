package futsalLocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

import com.example.hamrofutsal.R
import com.example.hamrofutsal.UserBookingActivity
import com.example.hamrofutsal.UserDashboardActivity


class NationalFutsal : AppCompatActivity() {
    private lateinit var BookNow : Button
    private lateinit var OpenMap: Button
    private lateinit var back: ImageView
    private lateinit var email : TextView
    private lateinit var phone : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_national_futsal)
        BookNow = findViewById(R.id.BookNow)
        OpenMap = findViewById(R.id.OpenMap)
        back = findViewById(R.id.backManakamana)
        phone = findViewById(R.id.phoneId)
//        checkPermissions()
        back.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        BookNow.setOnClickListener {
            val intent = Intent(this, UserBookingActivity::class.java)
            startActivity(intent)
        }
        OpenMap.setOnClickListener {
            // Place your click listener logic here
            // This code will execute when the OpenMap button is clicked
            val composeView = findViewById<ComposeView>(R.id.compose_view_map)
            composeView.setContent {
                val mapUrl = "/M8GM%2B26R+National+Sports+Centre+Pvt.+Ltd.,+Lalitpur+44600/@27.679533,85.3083263,13z/data=!3m1!4b1!4m17!1m7!3m6!1s0x39eb19ea00ef9c93:0xc6689d8e05ef526a!2sNational+Sports+Centre+Pvt.+Ltd.!8m2!3d27.6770538!4d85.3338971!16s%2Fg%2F11cs1vp11g!4m8!1m1!4e1!1m5!1m1!1s0x39eb19ea00ef9c93:0xc6689d8e05ef526a!2m2!1d85.3338971!2d27.6770538?entry=ttu"
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@setContent
                }
                CurrentLocationContent(usePreciseLocation = true,mapUrl)
            }
        }
        phone.setOnClickListener{
            val composeView = findViewById<ComposeView>(R.id.compose_view_phone)
            composeView.setContent {

                InitiatePhoneCall(phone=phone.text.toString())
            }
        }

    }


}
