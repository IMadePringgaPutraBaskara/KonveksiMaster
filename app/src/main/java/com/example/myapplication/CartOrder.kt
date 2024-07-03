package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartOrder : AppCompatActivity() {

    private lateinit var itemCountTextView: TextView
    private lateinit var totalPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_order)

        // Inisialisasi TextView menggunakan findViewById
        itemCountTextView = findViewById(R.id.itemCountTextView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)

        // Menerima data dari Intent
        val extras = intent.extras
        if (extras != null) {
            val itemCount = extras.getInt("itemCount", 0) // Mengambil jumlah item
            val totalPrice = extras.getInt("totalPrice", 0) // Mengambil total harga

            // Gunakan data yang diterima untuk menampilkan informasi di CartOrder Activity
            itemCountTextView.text = "Jumlah Item: $itemCount"
            totalPriceTextView.text = "Total Harga: Rp $totalPrice"
        }
    }
}
