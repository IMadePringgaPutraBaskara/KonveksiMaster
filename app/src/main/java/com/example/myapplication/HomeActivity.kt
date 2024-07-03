package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_app)

        val imageShirt = findViewById<ImageView>(R.id.imageShirt)
        val imageTShirt = findViewById<ImageView>(R.id.imageTShirt)
        val imagePolo = findViewById<ImageView>(R.id.imagePolo)
        val imageHoodie = findViewById<ImageView>(R.id.imageHoodie)

        imageShirt.setOnClickListener {
            // Tambahkan aksi saat imageShirt diklik
            val intent = Intent(this@HomeActivity, ShirtSale::class.java)
            startActivity(intent)
        }

        imageTShirt.setOnClickListener {
            // Tambahkan aksi saat imageTShirt diklik
            val intent = Intent(this@HomeActivity, TshirtSale::class.java)
            startActivity(intent)
        }

        imagePolo.setOnClickListener {
            // Tambahkan aksi saat imagePolo diklik
            val intent = Intent(this@HomeActivity, PoloSale::class.java)
            startActivity(intent)
        }

        imageHoodie.setOnClickListener {
            // Tambahkan aksi saat imageHoodie diklik
            val intent = Intent(this@HomeActivity, HoodieSale::class.java)
            startActivity(intent)
        }
    }
}
