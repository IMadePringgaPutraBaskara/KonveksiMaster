package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class CartOrder : AppCompatActivity() {

    private lateinit var orderDateTextView: TextView
    private lateinit var estimatedDateTextView: TextView
    private lateinit var selectDateButton: Button
    private lateinit var submitButton: Button
    private lateinit var itemCountTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var itemTypeTextView: TextView
    private lateinit var shippingLocationEditText: EditText
    private lateinit var customerNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences

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
        shippingLocationEditText = findViewById(R.id.shippingLocationEditText)
        customerNameEditText = findViewById(R.id.customerNameEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE)

        // Menerima data dari Intent
        val itemCount = intent.getIntExtra("itemCount", 0)
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        val itemType = intent.getStringExtra("itemType") ?: ""

        // Menampilkan data yang diterima
        itemCountTextView.text = "Jumlah Item: $itemCount"
        totalPriceTextView.text = "Total Harga: $totalPrice"
        itemTypeTextView.text = "Jenis Item: $itemType"

        // Set onClickListener untuk button selectDateButton
        selectDateButton.setOnClickListener {
            showDatePicker()
        }

        // Set onClickListener untuk button submitButton
        submitButton.setOnClickListener {
            val customerName = customerNameEditText.text.toString()
            val shippingLocation = shippingLocationEditText.text.toString()
            val orderDate = orderDateTextView.text.toString().replace("Tanggal Pemesanan: ", "")
            val estimatedDate = estimatedDateTextView.text.toString().replace("Estimasi Tanggal Jadi: ", "")

            // Validasi input
            if (customerName.isBlank() || shippingLocation.isBlank() || orderDate.isBlank() || estimatedDate.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mendapatkan userId dari SharedPreferences
            val userId = sharedPreferences.getInt("user_id", 0)
            if (userId == 0) {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil submitOrder dengan data yang benar
            submitOrder(userId, itemType, itemCount, totalPrice, customerName, shippingLocation, orderDate, estimatedDate)
        }
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
                val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth" // Format tanggal bisa disesuaikan
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

        return "$estimatedYear-${String.format("%02d", estimatedMonth)}-${String.format("%02d", estimatedDay)}" // Format YYYY-MM-DD
    }

    private fun submitOrder(id_user: Int, jenis_produk: String, jumlah: Int, total_harga: Int, nama_pemesan: String, alamat: String, tgl_pesan: String, tgl_selesai: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL(Db_connection.urlTransaksi) // URL endpoint untuk menyimpan transaksi
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doOutput = true

            val data = "id_user=$id_user&jenis_produk=$jenis_produk&jumlah=$jumlah&nama_pemesan=$nama_pemesan&alamat=$alamat&tgl_pesan=$tgl_pesan&tgl_selesai=$tgl_selesai&total_harga=$total_harga"

            Log.d("CartOrder", "Data to be sent: $data")

            try {
                val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                outputStreamWriter.write(data)
                outputStreamWriter.flush()

                val responseCode = httpURLConnection.responseCode
                val responseMessage = httpURLConnection.responseMessage
                Log.d("CartOrder", "Response code: $responseCode, Response message: $responseMessage")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Membaca respons dari server
                    val response = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
                    Log.d("CartOrder", "Server response: $response")

                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    withContext(Dispatchers.Main) {
                        if (status == "success") {
                            // Jika berhasil, navigasi ke FinishOrder activity
                            val intent = Intent(this@CartOrder, FinishOrder::class.java)
                            startActivity(intent)
                            Toast.makeText(this@CartOrder, "Order submitted successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@CartOrder, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("CartOrder", "Failed to submit order: $responseCode, $responseMessage")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@CartOrder, "Failed to submit order: $responseMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("CartOrder", "Exception during submitOrder: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CartOrder, "Exception during submitOrder: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } finally {
                httpURLConnection.disconnect()
            }
        }
    }
}
