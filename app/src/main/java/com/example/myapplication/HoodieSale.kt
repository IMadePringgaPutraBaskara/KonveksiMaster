package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HoodieSale : AppCompatActivity() {

    private lateinit var value:TextView
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hoodie_sale)

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

