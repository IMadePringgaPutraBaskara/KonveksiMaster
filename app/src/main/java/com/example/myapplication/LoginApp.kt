package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LoginApp : AppCompatActivity() {

    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnSignupText: Button? = null

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_app)

        sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE)

        etUsername = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.loginButton)
        btnSignupText = findViewById(R.id.signupText)

        btnLogin?.setOnClickListener {
            val username = etUsername?.text.toString()
            val password = etPassword?.text.toString()

            if (!username.isEmpty() && !password.isEmpty()) {
                val url = "${Db_connection.urlLogin}?username=$username&password=$password"

                val stringRequest = StringRequest(Request.Method.GET, url,
                    { response ->
                        if (response.contains("success")) {
                            // Parsing response for id_user, assuming response contains id_user field
                            val idUser = "dummy_id_user" // Replace with actual id_user from response

                            // Simpan status login, username, dan id_user ke SharedPreferences
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("isLoggedIn", true)
                            editor.putString("username", username)
                            editor.putString("id_user", idUser)
                            editor.apply()

                            Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                            finish() // Mengakhiri activity login setelah berhasil login
                        } else {
                            Toast.makeText(applicationContext, "Login gagal: $response", Toast.LENGTH_SHORT).show()
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

        btnSignupText?.setOnClickListener {
            val intent = Intent(this, RegisterApp::class.java)
            startActivity(intent)
        }

        // Cek apakah pengguna sudah login sebelumnya, jika ya langsung arahkan ke HomeActivity
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish() // Mengakhiri activity login jika pengguna sudah login sebelumnya
        }
    }
}