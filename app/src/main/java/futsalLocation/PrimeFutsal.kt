package futsalLocation

import android.Manifest
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


class PrimeFutsal : AppCompatActivity() {
    private lateinit var BookNow : Button
    private lateinit var OpenMap: Button
    private lateinit var back: ImageView
    private lateinit var email : TextView
    private lateinit var phone : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prime_futsal)
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
                val mapUrl = "/Prime+Futsal+Gyaneshwor,+Jayanti+Galli,+Kathmandu+44600/@27.6936416,85.3032786,13z/data=!3m1!4b1!4m17!1m7!3m6!1s0x39eb191ace459b7d:0xa840c9538cc417f8!2sPrime+Futsal+Gyaneshwor!8m2!3d27.7105278!4d85.3347222!16s%2Fg%2F11hf775nbf!4m8!1m1!4e1!1m5!1m1!1s0x39eb191ace459b7d:0xa840c9538cc417f8!2m2!1d85.3347222!2d27.7105278?entry=ttu"
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
