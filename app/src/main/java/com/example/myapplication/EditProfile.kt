package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etTelNumber: EditText
    private lateinit var btnUpdateSave: Button
    private lateinit var btnDeleteAccount: Button
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_user)

        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE)

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        etTelNumber = findViewById(R.id.etTelNumber)
        btnUpdateSave = findViewById(R.id.bntUpdateSave)
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount)
        back = findViewById(R.id.backProfileMenu)

        // Load current user data into EditTexts
        loadUserData()

        // Set OnClickListener for back button
        back.setOnClickListener {
            val intent = Intent(this@EditProfile, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set OnClickListener for save button
        btnUpdateSave.setOnClickListener {
            saveUserData()
        }

        // Set OnClickListener for delete account button
        btnDeleteAccount.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun loadUserData() {
        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")
        val address = sharedPreferences.getString("address", "")
        val telNumber = sharedPreferences.getString("tel_number", "")

        etUsername.setText(username)
        etEmail.setText(email)
        etAddress.setText(address)
        etTelNumber.setText(telNumber)
    }

    private fun saveUserData() {
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val address = etAddress.text.toString()
        val telNumber = etTelNumber.text.toString()

        if (username.isEmpty() || email.isEmpty() || address.isEmpty() || telNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("address", address)
        editor.putString("tel_number", telNumber)
        editor.apply()

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete your account?")

        builder.setPositiveButton("Delete") { dialog, which ->
            // Clear SharedPreferences
            sharedPreferences.edit().clear().apply()

            // Redirect to login screen
            val intent = Intent(this@EditProfile, LoginApp::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Close this activity to prevent going back
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
