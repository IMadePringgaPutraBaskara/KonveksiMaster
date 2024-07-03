package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView

class OpenApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.open_app)

        val elevatedButton: Button = findViewById(R.id.elevatedButton)
        elevatedButton.setOnClickListener {
            // Create an Intent to start LoginApp
            val intent = Intent(this, LoginApp::class.java)
            startActivity(intent)
        }
        }

}
