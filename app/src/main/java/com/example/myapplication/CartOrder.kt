package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CartOrder : AppCompatActivity() {

    private lateinit var orderDateTextView: TextView
    private lateinit var estimatedDateTextView: TextView
    private lateinit var selectDateButton: Button
    private lateinit var submitButton: Button
    private lateinit var itemCountTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var itemTypeTextView: TextView
    private lateinit var back: ImageView
    private var originActivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_order)

        // Inisialisasi view
        orderDateTextView = findViewById(R.id.orderDateTextView)
        estimatedDateTextView = findViewById(R.id.estimatedDateTextView)
        selectDateButton = findViewById(R.id.selectDateButton)
        submitButton = findViewById(R.id.submitButton)
        itemCountTextView = findViewById(R.id.itemCountTextView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        itemTypeTextView = findViewById(R.id.itemTypeTextView)
        back = findViewById(R.id.back) // Inisialisasi view back

        // Ambil informasi aktivitas asal dari intent
        originActivity = intent.getStringExtra("originActivity")

        // Set onClickListener untuk button selectDateButton
        selectDateButton.setOnClickListener {
            showDatePicker()
        }

        // Set onClickListener untuk button submitButton
        submitButton.setOnClickListener {
            val intent = Intent(this, FinishOrder::class.java)
            startActivity(intent)
        }

        // Set onClickListener untuk ImageView back
        back.setOnClickListener {
            // Bergantung pada aktivitas asal, kembalikan ke aktivitas yang sesuai
            when (originActivity) {
                "TshirtSale" -> {
                    val intent = Intent(this, TshirtSale::class.java)
                    startActivity(intent)
                }
                "ShirtSale" -> {
                    val intent = Intent(this, ShirtSale::class.java)
                    startActivity(intent)
                }
                "PoloSale" -> {
                    val intent = Intent(this, PoloSale::class.java)
                    startActivity(intent)
                }
                "HoodieSale" -> {
                    val intent = Intent(this, HoodieSale::class.java)
                    startActivity(intent)
                }
                // Tambahkan kasus lainnya sesuai dengan aktivitas asal lainnya
                else -> {
                    // Defaultnya kembali ke HomeActivity jika tidak ada informasi asal
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }

            finish() // Tutup aktivitas saat ini untuk menghindari kembali ke sana
        }

        // Menerima data dari intent
        val itemCount = intent.getIntExtra("itemCount", 0)
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        val itemType = intent.getStringExtra("itemType")

        // Menampilkan data yang diterima
        itemCountTextView.text = "Jumlah Item: $itemCount"
        totalPriceTextView.text = "Total Harga: $totalPrice"
        itemTypeTextView.text = "Jenis Item: $itemType"
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create DatePickerDialog
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Update orderDateTextView dengan tanggal yang dipilih
                val selectedDate = "$dayOfMonth-${monthOfYear + 1}-$year" // Format tanggal bisa disesuaikan
                orderDateTextView.text = "Tanggal Pemesanan: $selectedDate"

                // Hitung estimasi tanggal jadi (contoh: 14 hari setelah tanggal pemesanan)
                val estimatedDate = calculateEstimatedDate(year, monthOfYear, dayOfMonth)
                estimatedDateTextView.text = "Estimasi Tanggal Jadi: $estimatedDate"
            }, year, month, day)

        // Tampilkan dialog
        datePickerDialog.show()
    }

    private fun calculateEstimatedDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        calendar.add(Calendar.DAY_OF_MONTH, 14) // 14 hari setelah tanggal pemesanan

        val estimatedYear = calendar.get(Calendar.YEAR)
        val estimatedMonth = calendar.get(Calendar.MONTH) + 1 // Month dimulai dari 0, jadi tambahkan 1
        val estimatedDay = calendar.get(Calendar.DAY_OF_MONTH)

        return "$estimatedDay-$estimatedMonth-$estimatedYear"
    }
}
