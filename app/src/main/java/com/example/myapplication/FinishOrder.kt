package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class FinishOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finish_order)

        Handler().postDelayed({ // Start the main activity after the splash screen duration
            val mainIntent = Intent(
                this@FinishOrder,
                HomeActivity::class.java
            )
            this@FinishOrder.startActivity(mainIntent)
            this@FinishOrder.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 3000 // Duration of wait in milliseconds
    }
}