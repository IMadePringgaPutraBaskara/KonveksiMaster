package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AdminMain : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_main)

        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE) // Inisialisasi SharedPreferences

        val btnOrderList = findViewById<Button>(R.id.btnOrderList)
        val btnUserList = findViewById<Button>(R.id.btnUserList)
        val btnLogoutAdmin = findViewById<Button>(R.id.LogoutAdmin)

        btnOrderList.setOnClickListener {
            // Navigate to Order List Activity
            val intent = Intent(this, AdminOrder::class.java)
            startActivity(intent)
        }

        btnUserList.setOnClickListener {
            // Navigate to User List Activity
            val intent = Intent(this, UserList::class.java)
            startActivity(intent)
        }

        btnLogoutAdmin.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin logout?")

        builder.setPositiveButton("Logout") { dialog, which ->
            // Menghapus session (clear SharedPreferences)
            sharedPreferences.edit().clear().apply()

            // Redirect ke halaman login
            val intent = Intent(this, LoginApp::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Menutup activity saat ini agar tidak dapat kembali dengan tombol back
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
