package UserMenu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hamrofutsal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserProfileActivity : AppCompatActivity() {
    private lateinit var update: Button
    private lateinit var deleteAccount: Button
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var address: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_user_profile)

        update = findViewById(R.id.Update)
        deleteAccount = findViewById(R.id.Delete)
        username = findViewById(R.id.UserName)
        email = findViewById(R.id.email)
        address = findViewById(R.id.address)

        // Set a click listener for the update button
        update.setOnClickListener {
            updateUserData()
        }

        // Set a click listener for the delete account button
        deleteAccount.setOnClickListener {
            showDeleteAccountConfirmationDialog()
        }
    }

    private fun updateUserData() {
        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            // Get the updated values from EditText fields
            val updatedUsername = username.text.toString()
            val updatedEmail = email.text.toString()
            val updatedAddress = address.text.toString()

            // Create a map to update specific fields
            val updatedData = mutableMapOf<String, Any?>()
            updatedData["name"] = updatedUsername
            updatedData["email"] = updatedEmail
            updatedData["address"] = updatedAddress

            // Reference to the user's node in the database
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

            // Update the user data
            userRef.updateChildren(updatedData)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "User data updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Failed to update user data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun showDeleteAccountConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Account Deletion")
        builder.setMessage("Are you sure you want to delete your account? This action cannot be undone.")

        builder.setPositiveButton("Yes") { _, _ ->
            // User confirmed, proceed with account deletion
            deleteAccount()
        }

        builder.setNegativeButton("No") { _, _ ->
            // User canceled the account deletion
            val intent = Intent(this,this::class.java)
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteAccount() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.delete()
            ?.addOnSuccessListener {
                // Account deleted successfully
                // You can also remove user data from the Realtime Database if needed
                removeUserDataFromDatabase()

                Toast.makeText(
                    this,
                    "Account deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()

                System.exit(0)
            }
            ?.addOnFailureListener {
                // Failed to delete the account
                Toast.makeText(
                    this,
                    "Failed to delete account: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun removeUserDataFromDatabase() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            // Reference to the user's node in the database
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

            // Remove user data from the database
            userRef.removeValue()
                .addOnSuccessListener {
                    System.exit(0)
                }
                .addOnFailureListener {
                    Toast.makeText(this,"could not perform!!",Toast.LENGTH_SHORT).show()
                }
        }
    }
}