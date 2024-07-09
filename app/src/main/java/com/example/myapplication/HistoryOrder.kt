package com.example.myapplication

import android.content.Context
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
import com.example.myapplication.adapter.TransactionAdapter
import com.example.myapplication.models.Transaction
import org.json.JSONArray

class HistoryOrder : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionList: ArrayList<Transaction>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_order)

        sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        transactionList = ArrayList()

        // Setup the adapter for the RecyclerView
        adapter = TransactionAdapter(transactionList)
        recyclerView.adapter = adapter

        // Mendapatkan userId dari SharedPreferences
        val userId = sharedPreferences.getInt("user_id", 0)
        if (userId != 0) {
            fetchTransactions(userId)
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        // Setup the back button
        val backProfileMenu: ImageView = findViewById(R.id.backProfileMenu)
        backProfileMenu.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchTransactions(userId: Int) {
        val url = "${Db_connection.urlGetTransaksi}?id=$userId"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    transactionList.clear()
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
                    adapter.notifyDataSetChanged()
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
}
