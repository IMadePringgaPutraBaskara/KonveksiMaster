package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class AdminUserList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserListAdapter
    private val userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_user_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserListAdapter(userList) { user -> onUserClick(user) }
        recyclerView.adapter = userAdapter

        // Set up the back button
        val backProfileMenu: ImageView = findViewById(R.id.backProfileMenu)
        backProfileMenu.setOnClickListener {
            onBackPressed()
        }

        fetchUsers()
    }

    private fun fetchUsers() {
        val url = "${Db_connection.urlGetAllUser}"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { response ->
                try {
                    val jsonArray = JSONArray(response)
                    userList.clear()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val username = jsonObject.getString("username")
                        val email = jsonObject.getString("email")
                        val address = jsonObject.getString("alamat")
                        val telNumber = jsonObject.getString("no_telp")
                        val status = jsonObject.getString("status") // Menambahkan status
                        val user = User(id, username, email, address, telNumber, status) // Menambahkan status ke User
                        userList.add(user)
                    }
                    userAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }


    private fun onUserClick(user: User) {
        val intent = Intent(this, AdminUserEdit::class.java)
        intent.putExtra("user", user) // Mengirimkan objek user yang lengkap
        startActivity(intent)
    }
}
