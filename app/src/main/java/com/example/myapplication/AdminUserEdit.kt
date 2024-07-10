package com.example.myapplication

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

class AdminUserEdit : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etTelNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etStatus: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var btnDeleteUser: Button  // Button untuk delete user
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_user_edit)

        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        etTelNumber = findViewById(R.id.etTelNumber)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        etStatus = findViewById(R.id.etStatus)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)  // Inisialisasi button delete user

        // Set up the back button
        val backProfileMenu: ImageView = findViewById(R.id.backProfileMenu)
        backProfileMenu.setOnClickListener {
            onBackPressed()
        }

        val user = intent.getParcelableExtra<User>("user")
        user?.let {
            userId = it.id
            etUsername.setText(it.username)
            etEmail.setText(it.email)
            etAddress.setText(it.address)
            etTelNumber.setText(it.telNumber)
            etStatus.setText(it.status)
        }

        btnSaveChanges.setOnClickListener {
            saveUserData()
        }

        btnDeleteUser.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun saveUserData() {
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val address = etAddress.text.toString()
        val telNumber = etTelNumber.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        val status = etStatus.text.toString()

        if (username.isEmpty() || email.isEmpty() || address.isEmpty() || telNumber.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isNotEmpty() && password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (status != "user" && status != "admin") {
            Toast.makeText(this, "Status must be 'user' or 'admin'", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "${Db_connection.urlAdminEditUser}"
        Log.d("AdminUserEdit", "URL: $url")

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("AdminUserEdit", "API Response: $response")
                try {
                    if (!response.startsWith("{")) {
                        Log.e("AdminUserEdit", "Unexpected response format")
                        Toast.makeText(this, "Unexpected response format", Toast.LENGTH_SHORT).show()
                        return@Listener
                    }

                    val jsonResponse = JSONObject(response)
                    val responseStatus = jsonResponse.optString("status", "error")  // Use optString with default value
                    val message = jsonResponse.optString("message", "Unknown error")  // Use optString with default value

                    if (responseStatus == "success") {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        finish()  // Close this activity to return to the previous one
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("AdminUserEdit", "Error: ${error.message}")
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
                params["status"] = status
                if (password.isNotEmpty()) {
                    params["password"] = password
                }
                Log.d("AdminUserEdit", "Params: $params")
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete this user?")
            .setPositiveButton("Delete") { _, _ -> deleteUser() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteUser() {
        val url = "${Db_connection.urlDeleteAccount}"
        Log.d("AdminUserEdit", "Delete URL: $url")

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("AdminUserEdit", "Delete API Response: $response")
                try {
                    if (!response.startsWith("{")) {
                        Log.e("AdminUserEdit", "Unexpected response format")
                        Toast.makeText(this, "Unexpected response format", Toast.LENGTH_SHORT).show()
                        return@Listener
                    }

                    val jsonResponse = JSONObject(response)
                    val responseStatus = jsonResponse.optString("status", "error")  // Use optString with default value
                    val message = jsonResponse.optString("message", "Unknown error")  // Use optString with default value

                    if (responseStatus == "success") {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        finish()  // Close this activity to return to the previous one
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("AdminUserEdit", "Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = userId.toString()
                Log.d("AdminUserEdit", "Delete Params: $params")
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
