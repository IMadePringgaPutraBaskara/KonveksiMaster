package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var telNumberTextView: TextView
    private lateinit var addressTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)

        // Inisialisasi View
        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        val btnHistoryOrder = findViewById<Button>(R.id.btnHistoryOrder)

        userNameTextView = findViewById(R.id.textViewUsername)
        emailTextView = findViewById(R.id.textViewEmail)
        telNumberTextView = findViewById(R.id.textViewTelNumber)
        addressTextView = findViewById(R.id.textViewAddress)

        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 0)

        Log.d("ProfileActivity", "user_id: $userId")

        if (userId == 0) {
            Toast.makeText(this, "User ID tidak ditemukan. Silakan login kembali.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginApp::class.java)
            startActivity(intent)
            finish()
        } else {
            val requestQueue = Volley.newRequestQueue(this)
            fetchUserData(userId, requestQueue)
        }

        // Set OnClickListener untuk tombol btnEditProfile
        btnEditProfile.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfile::class.java)
            startActivity(intent)
        }

        // Set OnClickListener untuk tombol btnHistoryOrder
        btnHistoryOrder.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HistoryOrder::class.java)
            startActivity(intent)
        }

        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.history -> {
                    startActivity(Intent(this, HistoryOrder::class.java))
                    true
                }
                R.id.logout -> {
                    showLogoutConfirmationDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchUserData(userId: Int, requestQueue: RequestQueue) {
        val url = "${Db_connection.urlGetUser}?id=$userId"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)

                    if (jsonObject.has("error")) {
                        userNameTextView.text = "Error: ${jsonObject.getString("error")}"
                    } else {
                        val username = jsonObject.getString("username")
                        val email = jsonObject.getString("email")
                        val noTelp = jsonObject.getString("no_telp")
                        val alamat = jsonObject.getString("alamat")

                        userNameTextView.text = username
                        emailTextView.text = email
                        telNumberTextView.text = noTelp
                        addressTextView.text = alamat
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(stringRequest)
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin logout?")

        builder.setPositiveButton("Logout") { dialog, which ->
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this@ProfileActivity, LoginApp::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
