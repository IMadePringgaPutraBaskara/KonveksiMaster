package com.example.myapplication

import android.content.Intent
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

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignupText: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_app) // Pastikan layout yang sesuai

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
                        if (response.contains("success")) {
                            Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
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

        btnSignupText.setOnClickListener {
            val intent = Intent(this, RegisterApp::class.java)
            startActivity(intent)
        }
    }
}
