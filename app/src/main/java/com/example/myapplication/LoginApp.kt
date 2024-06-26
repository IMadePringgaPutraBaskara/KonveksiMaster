package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_app) // Pastikan layout yang sesuai

        // Inisialisasi tombol untuk register
        val signupButton = findViewById<Button>(R.id.signupText)
        signupButton.setOnClickListener {
            val intent = Intent(this, RegisterApp::class.java)
            startActivity(intent)
        }
    }
}
