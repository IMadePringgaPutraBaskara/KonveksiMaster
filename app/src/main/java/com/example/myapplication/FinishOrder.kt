package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FinishOrder : AppCompatActivity() {

    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finish_order)

        backButton = findViewById(R.id.buttonBackToHome)

        // Set onClickListener untuk button backButton
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Opsional: untuk menutup aktivitas saat ini sehingga tidak dapat kembali ke sana
        }
    }
}
