package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_app)

        sharedPreferences = getSharedPreferences("login_pref", MODE_PRIVATE)

        // Ambil nama pengguna dari SharedPreferences
        val username = sharedPreferences.getString("username", "Guest")

        // Set TextView user_greeting dengan pesan selamat datang yang mencakup nama pengguna
        val userGreetingTextView = findViewById<TextView>(R.id.user_greeting)
        userGreetingTextView.text = "Selamat Datang, $username"

        // Setup ImageView listeners seperti yang Anda lakukan sebelumnya
        val imageShirt = findViewById<ImageView>(R.id.imageShirt)
        val imageTShirt = findViewById<ImageView>(R.id.imageTShirt)
        val imagePolo = findViewById<ImageView>(R.id.imagePolo)
        val imageHoodie = findViewById<ImageView>(R.id.imageHoodie)

        imageShirt.setOnClickListener {
            val intent = Intent(this@HomeActivity, ShirtSale::class.java)
            startActivity(intent)
        }

        imageTShirt.setOnClickListener {
            val intent = Intent(this@HomeActivity, TshirtSale::class.java)
            startActivity(intent)
        }

        imagePolo.setOnClickListener {
            val intent = Intent(this@HomeActivity, PoloSale::class.java)
            startActivity(intent)
        }

        imageHoodie.setOnClickListener {
            val intent = Intent(this@HomeActivity, HoodieSale::class.java)
            startActivity(intent)
        }

        // Setup logout button
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
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
            val intent = Intent(this@HomeActivity, LoginApp::class.java)
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
