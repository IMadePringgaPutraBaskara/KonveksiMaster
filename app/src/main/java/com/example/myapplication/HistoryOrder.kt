package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.google.android.material.bottomnavigation.BottomNavigationView

class HistoryOrder : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionList: ArrayList<Transaction>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_order)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("login_pref", Context.MODE_PRIVATE)

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        transactionList = ArrayList()
        adapter = TransactionAdapter(transactionList)
        recyclerView.adapter = adapter

        // Get userId from SharedPreferences
        val userId = sharedPreferences.getInt("user_id", 0)
        if (userId != 0) {
            fetchTransactions(userId)
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
        }

        // Set up BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.history // Set 'history' as selected item by default

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.history -> {
                    // Already in HistoryOrder, do nothing
                    true
                }
                R.id.logout -> {
                    showLogoutConfirmationDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin logout?")

        builder.setPositiveButton("Logout") { dialog, _ ->
            // Clear SharedPreferences
            sharedPreferences.edit().clear().apply()

            // Redirect to LoginActivity
            val intent = Intent(this, LoginApp::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Close this activity to prevent returning to it
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
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
