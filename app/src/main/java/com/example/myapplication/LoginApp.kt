package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginApp : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignupText: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_app)

        sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE)

        etUsername = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.loginButton)
        btnSignupText = findViewById(R.id.signupText)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val url = "${Db_connection.urlLogin}?username=$username&password=$password"

                val stringRequest = StringRequest(Request.Method.GET, url,
                    { response ->
                        Log.d("LoginApp", "Response: $response") // Tambahkan log ini untuk melihat respons
                        try {
                            val jsonObject = JSONObject(response)
                            val status = jsonObject.getString("status")
                            if (status == "success") {
                                val idUser = jsonObject.getString("userId")
                                val userStatus = jsonObject.getString("userStatus") // Mendapatkan status pengguna
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn", true)
                                editor.putString("username", username)
                                editor.putString("id_user", idUser)
                                editor.putString("userStatus", userStatus) // Menyimpan status pengguna
                                editor.apply()

                                Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                                if (userStatus == "admin") {
                                    val intent = Intent(applicationContext, AdminMain::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(applicationContext, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                                finish()
                            } else {
                                Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, "Error parsing response: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    },
                    { error ->
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                    })

                val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(applicationContext, "Username atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignupText.setOnClickListener {
            val intent = Intent(this, RegisterApp::class.java)
            startActivity(intent)
        }

        // Cek apakah pengguna sudah login sebelumnya, jika ya langsung arahkan ke HomeActivity atau AdminMain
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            val userStatus = sharedPreferences.getString("userStatus", "user")
            val intent = if (userStatus == "admin") {
                Intent(applicationContext, AdminMain::class.java)
            } else {
                Intent(applicationContext, HomeActivity::class.java)
            }
            startActivity(intent)
            finish() // Mengakhiri activity login jika pengguna sudah login sebelumnya
        }
    }
}
