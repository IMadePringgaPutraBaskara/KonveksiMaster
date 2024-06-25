package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginApp : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.before_login)

        val button = findViewById<Button>(R.id.signupText)
        button.setOnClickListener {
            // Menggunakan BeforeLog::class.java untuk intent
            val intent = Intent(this, RegisterApp::class.java)
            startActivity(intent)
        }
    }
}