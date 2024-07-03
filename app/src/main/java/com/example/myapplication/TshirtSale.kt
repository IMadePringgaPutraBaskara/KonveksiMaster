package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TshirtSale : AppCompatActivity() {

    private lateinit var value:TextView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tshirt_sale)

        value = findViewById(R.id.value)
    }

    fun increasement(v: View){
        count++
        value.text = count .toString()
    }

    fun decreasement(v: View){
        if (count > 0) count--
        value.text = count.toString()
    }
}