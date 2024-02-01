package UserData

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.hamrofutsal.R
import com.example.hamrofutsal.UserDashboardActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserInformation : AppCompatActivity() {
    private lateinit var fullNameET: EditText
    private lateinit var emailET: EditText
    private lateinit var addressET: EditText
    private lateinit var phoneEt: EditText
    private lateinit var confirmButton : Button

    private lateinit var sharedPref: SharedPreferences

    private lateinit var fullName : String
    private lateinit var email : String
    private lateinit var address : String
    private lateinit var phoneNumber : String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        fullNameET = findViewById(R.id.fullName)
        emailET = findViewById(R.id.email)
        addressET = findViewById(R.id.address)
        confirmButton = findViewById(R.id.confirmButton)

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        fullName = ""
        email = ""
        address= ""





        confirmButton.setOnClickListener {
            fullName = fullNameET.text.toString()
            email = emailET.text.toString()
            address = addressET.text.toString()
            phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
            if(fullName.isEmpty()&&email.isEmpty()&&address.isEmpty()) {
                Toast.makeText(this@UserInformation, "Please, fill all the field!", Toast.LENGTH_SHORT).show()
            }
            else {
                saveUserToFirebase(fullName,email,address,phoneNumber)
                val intent = Intent(this@UserInformation, UserDashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



        //phoneNumber
    }

    @SuppressLint("SuspiciousIndentation")
    private fun saveUserToFirebase(fullName: String, email: String, address: String, phoneNumber: String) {
        Log.d("OtpActivity", "Saving user data to Firebase")
        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser?.let { user ->
            user.uid?.let { uid ->
                val usersRef = FirebaseDatabase.getInstance().getReference("users")
                val user = Users(
                    name = fullName,
                    phoneNumber = phoneNumber,
                    email = email,
                    address = address
                )
                saveUserInfoPref(fullName, email, address, uid)
                usersRef.child(uid).setValue(user)
            }
        }
    }

    private fun saveUserInfoPref(fullName: String, email: String, address: String, uid: String) {
        val editor = sharedPref.edit()
        editor.putString("fullName", fullName)
        editor.putString("email", email)
        editor.putString("address", address)
        editor.putString("uid", uid)
        editor.apply()
    }
}