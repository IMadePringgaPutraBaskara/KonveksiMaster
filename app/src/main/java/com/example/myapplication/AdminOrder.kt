package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.adapter.AdminTransactionAdapter
import com.example.myapplication.models.Transaction
import org.json.JSONArray

class AdminOrder : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminTransactionAdapter
    private lateinit var transactionList: ArrayList<Transaction>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var backButton: ImageView  // Tambahkan variabel untuk tombol back

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_order)  // Pastikan nama layout file ini sesuai

        sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        recyclerView = findViewById(R.id.recyclerView)
        backButton = findViewById(R.id.backProfileMenu)  // Inisialisasi tombol back

        recyclerView.layoutManager = LinearLayoutManager(this)
        transactionList = ArrayList()

        // Set OnClickListener untuk tombol back
        backButton.setOnClickListener {
            onBackPressed()
        }

        fetchTransactions()
    }

    private fun fetchTransactions() {
        val url = Db_connection.urlGetAllTransaksi  // Sesuaikan dengan URL API Anda

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val transaction = Transaction(
                            jsonObject.getInt("id_transaksi"),
                            jsonObject.getInt("id_user"),
                            jsonObject.getString("jenis_produk"),
                            jsonObject.getInt("jumlah"),
                            jsonObject.getInt("total_harga"),
                            jsonObject.getString("nama_pemesan"),
                            jsonObject.getString("alamat"),
                            jsonObject.getString("tgl_pesan"),
                            jsonObject.getString("tgl_selesai")
                        )
                        transactionList.add(transaction)
                    }
                    adapter = AdminTransactionAdapter(transactionList)
                    recyclerView.adapter = adapter
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Menambahkan logging atau operasi lain jika diperlukan
        // Misalnya:
        // Log.d("AdminOrder", "Back button pressed")
    }
}
