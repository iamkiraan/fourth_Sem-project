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
import androidx.core.app.ActivityCompat
import com.example.hamrofutsal.CurrentLocationContent

import com.example.hamrofutsal.R
import com.example.hamrofutsal.UserBookingActivity
import com.example.hamrofutsal.UserDashboardActivity


var check : Boolean = false
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
            val composeView = findViewById<ComposeView>(R.id.compose_view)
            composeView.setContent {
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
                CurrentLocationContent(usePreciseLocation = true)
            }
        }
//        phone.setOnClickListener{
//            val phoneNumber = "9865445343" // Replace this with your actual phone number
//            val callIntent = Intent(Intent.ACTION_CALL)
//            callIntent.data = Uri.parse("tel:$phoneNumber")
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                startActivity(callIntent)
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
//            }
//        }
//
//
//    }

//    private fun checkPermissions() {
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),101)
//        }
//    }

    }


}