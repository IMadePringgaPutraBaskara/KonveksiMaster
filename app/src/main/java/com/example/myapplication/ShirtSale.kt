package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ShirtSale : AppCompatActivity() {

    private lateinit var valueTextView: TextView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shirt_sale)

        valueTextView = findViewById(R.id.value)
        val orderButton: Button = findViewById(R.id.orderButton)
        val backImageView : ImageView= findViewById(R.id.back)
        val profileImageView : ImageView = findViewById(R.id.profile)

        orderButton.setOnClickListener {
            val intent = Intent(this, CartOrder::class.java)
            intent.putExtra("itemCount", count)
            intent.putExtra("totalPrice", calculateTotalPrice())
            intent.putExtra("itemType", "Shirt") // Tambahkan itemType
            startActivity(intent)
        }
        backImageView.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Menutup activity saat ini untuk menghindari kembali ke sini
        }
        profileImageView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish() // Menutup activity saat ini untuk menghindari kembali ke sini
        }
    }

    private fun calculateTotalPrice(): Int {
        return count * 100000 // Harga per item
    }

    fun increasement(v: View) {
        count++
        valueTextView.text = count.toString()
    }

    fun decreasement(v: View) {
        if (count > 0) count--
        valueTextView.text = count.toString()
    }
}
