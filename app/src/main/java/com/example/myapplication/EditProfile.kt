package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class EditProfile : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etTelNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnUpdateSave: Button
    private lateinit var btnDeleteAccount: Button
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_user)

        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 0)  // Ambil user ID dari SharedPreferences

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        etTelNumber = findViewById(R.id.etTelNumber)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnUpdateSave = findViewById(R.id.bntUpdateSave)
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount)
        back = findViewById(R.id.backProfileMenu)

        // Load current user data into EditTexts
        loadUserData()

        // Set OnClickListener for back button
        back.setOnClickListener {
            onBackPressed() // Go back to the previous activity
        }

        // Set OnClickListener for save button
        btnUpdateSave.setOnClickListener {
            saveUserData(userId)
        }

        // Set OnClickListener for delete account button
        btnDeleteAccount.setOnClickListener {
            showDeleteConfirmationDialog(userId)
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

    private fun saveUserData(userId: Int) {
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val address = etAddress.text.toString()
        val telNumber = etTelNumber.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (username.isEmpty() || email.isEmpty() || address.isEmpty() || telNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isNotEmpty() && password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "${Db_connection.urlUpdateUser}"

        Log.d("EditProfile", "URL: $url")

        // Create a StringRequest to send as the request body
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("EditProfile", "Response: $response")
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    if (status == "success") {
                        val editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.putString("email", email)
                        editor.putString("address", address)
                        editor.putString("tel_number", telNumber)
                        // Update password if it's changed
                        if (password.isNotEmpty()) {
                            editor.putString("password", password)
                        }
                        editor.apply()

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        // Go back to the previous activity
                        finish() // Close this activity
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("EditProfile", "Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = userId.toString()
                params["username"] = username
                params["email"] = email
                params["alamat"] = address
                params["no_telp"] = telNumber
                if (password.isNotEmpty()) {
                    params["password"] = password
                }
                Log.d("EditProfile", "Params: $params")
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showDeleteConfirmationDialog(userId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete your account?")

        builder.setPositiveButton("Delete") { dialog, _ ->
            deleteAccount(userId)
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteAccount(userId: Int) {
        val url = Db_connection.urlDeleteAccount
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("EditProfile", "Delete Response: $response")
                try {
                    val jsonResponse = JSONObject(response)
                    val message = jsonResponse.getString("message")

                    if (message == "User berhasil dihapus") {
                        // Clear SharedPreferences
                        sharedPreferences.edit().clear().apply()

                        // Redirect to login screen
                        val intent = Intent(this@EditProfile, LoginApp::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish() // Close this activity to prevent going back
                    } else {
                        Toast.makeText(this, jsonResponse.getString("error"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("EditProfile", "Delete Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = userId.toString()
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
