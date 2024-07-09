package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AdminUserEdit : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etTelNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSaveChanges: Button
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
        btnSaveChanges = findViewById(R.id.btnSaveChanges)

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
        }

        btnSaveChanges.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {
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

        val url = "${Db_connection.urlUpdateUser}$userId"

        Log.d("AdminUserEdit", "URL: $url")

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.d("AdminUserEdit", "API Response: $response")
                try {
                    if (response == "Update Berhasil") {
                        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                        finish()  // Close this activity to return to the previous one
                    } else {
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
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
                Log.d("AdminUserEdit", "Params: $params")
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
