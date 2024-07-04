package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_main)

        val btnOrderList = findViewById<Button>(R.id.btnOrderList)
        val btnUserList = findViewById<Button>(R.id.btnUserList)
        val btnLogoutAdmin = findViewById<Button>(R.id.LogoutAdmin)

        btnOrderList.setOnClickListener { // Navigate to Order List Activity
            val intent = Intent(
                this,
                OrderList::class.java
            )
            startActivity(intent)
        }

        btnUserList.setOnClickListener { // Navigate to User List Activity
            val intent = Intent(
                this,
                UserList::class.java
            )
            startActivity(intent)
        }

        btnLogoutAdmin.setOnClickListener { // Logout and navigate to Login Activity
            val intent = Intent(
                this,
                LoginApp::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}