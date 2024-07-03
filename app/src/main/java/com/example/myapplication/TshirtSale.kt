package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TshirtSale : AppCompatActivity() {

    private lateinit var valueTextView: TextView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tshirt_sale)

        valueTextView = findViewById(R.id.value)
        val orderButton: Button = findViewById(R.id.orderButton)

        orderButton.setOnClickListener {
            // Membuat Intent untuk pindah ke CartOrder Activity
            val intent = Intent(this, CartOrder::class.java)

            // Mengirim data ekstra ke CartOrder Activity
            intent.putExtra("itemCount", count)
            intent.putExtra("totalPrice", calculateTotalPrice())

            // Memulai Activity CartOrder
            startActivity(intent)
        }
    }

    // Contoh logika untuk menghitung harga total
    private fun calculateTotalPrice(): Int {
        return count * 50000 //
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
