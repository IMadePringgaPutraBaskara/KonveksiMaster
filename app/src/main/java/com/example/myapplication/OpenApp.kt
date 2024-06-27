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
        NavigationBarView.OnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_menu_utama -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.item_2 -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }
    }
}
