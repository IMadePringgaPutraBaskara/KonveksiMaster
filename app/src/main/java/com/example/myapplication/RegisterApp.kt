package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegisterApp : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etTelNumber: EditText
    private lateinit var etPass: EditText
    private lateinit var btnDaftar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_app) // Pastikan layout yang sesuai

        etUsername = findViewById(R.id.reg_username)
        etEmail = findViewById(R.id.email)
        etAddress = findViewById(R.id.reg_Address)
        etTelNumber = findViewById(R.id.tel_number)
        etPass = findViewById(R.id.reg_pass)
        btnDaftar = findViewById(R.id.regist_button)

        btnDaftar.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val address = etAddress.text.toString()
            val telNumber = etTelNumber.text.toString()
            val pass = etPass.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && telNumber.isNotEmpty() && pass.isNotEmpty()) {
                val stringRequest = object : StringRequest(
                    Method.POST, Db_connection.urlRegister,
                    Response.Listener { response ->
                        Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, LoginApp::class.java))
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                    }
                ) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["email"] = email
                        params["address"] = address
                        params["tel_number"] = telNumber
                        params["password"] = pass
                        return params
                    }
                }

                val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(applicationContext, "Ada data yang masih kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
