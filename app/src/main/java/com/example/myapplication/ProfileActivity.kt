package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private var back: ImageView? = null
    private var btnEditProfile: Button? = null
    private var btnHistoryOrder: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)  // Pastikan nama file layout benar

        // Inisialisasi View
        back = findViewById(R.id.backProfileMenu)
        btnEditProfile = findViewById(R.id.btnEditProfie)
        btnHistoryOrder = findViewById(R.id.btnHistoryOrder)

        // Set OnClickListener untuk tombol back
        back?.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set OnClickListener untuk tombol btnEditProfile
        btnEditProfile?.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfile::class.java)
            startActivity(intent)
        }

        // Set OnClickListener untuk tombol btnHistoryOrder
        btnHistoryOrder?.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HistoryOrder::class.java)
            startActivity(intent)
        }
    }
}
