package futsalLocation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.hamrofutsal.CurrentLocationContent

import com.example.hamrofutsal.R
import com.example.hamrofutsal.UserBookingActivity
import com.example.hamrofutsal.UserDashboardActivity


class ManakamanaFutsal : AppCompatActivity() {
    private lateinit var BookNow : Button
    private lateinit var OpenMap: Button
    private lateinit var back: ImageView
    private lateinit var email : TextView
    private lateinit var phone : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manakamana_futsal)
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
                val mapUrl = "/P9MQ%2B3GX+Manakamana+Futsal,+Gokarneshwor+44600/@27.7031882,85.332368,13z/data=!3m1!4b1!4m17!1m7!3m6!1s0x39eb1bd1f0853467:0x345900f774919c3c!2sManakamana+Futsal!8m2!3d27.7327285!4d85.388845!16s%2Fg%2F11h48hb416!4m8!1m1!4e1!1m5!1m1!1s0x39eb1bd1f0853467:0x345900f774919c3c!2m2!1d85.388845!2d27.7327285?entry=ttu"
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
@Composable
fun InitiatePhoneCall(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:$phone"))
    val context = LocalContext.current
    context.startActivity(intent)
}