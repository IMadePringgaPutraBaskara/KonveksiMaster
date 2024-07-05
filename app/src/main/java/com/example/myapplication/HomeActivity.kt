package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
        userGreetingTextView.text = "$username!"

        // Setup ImageView listeners seperti yang Anda lakukan sebelumnya
        val imageShirt = findViewById<ImageView>(R.id.imageShirt)
        val imageTShirt = findViewById<ImageView>(R.id.imageTShirt)
        val imagePolo = findViewById<ImageView>(R.id.imagePolo)
        val imageHoodie = findViewById<ImageView>(R.id.imageHoodie)
        val imageProfile = findViewById<ImageView>(R.id.ProfileMenu)

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

        imageProfile.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
