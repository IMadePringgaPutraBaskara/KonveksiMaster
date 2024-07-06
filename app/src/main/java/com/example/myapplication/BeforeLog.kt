package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.ImageAdapter
import com.example.myapplication.models.ImageItem
import java.util.UUID

class BeforeLog : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.before_login)

        Log.d("BeforeLog", "onCreate: Layout before_login.xml diinisialisasi")

        viewPager2 = findViewById(R.id.ViewPager2)
        val slideDotLL = findViewById<LinearLayout>(R.id.slideDotLL)

        Log.d("BeforeLog", "onCreate: ViewPager2 dan slideDotLL ditemukan")

        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.cheatcode
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.sporti
            )
        )

        Log.d("BeforeLog", "onCreate: ImageList diinisialisasi dengan ${imageList.size} gambar")

        val imageAdapter = ImageAdapter()
        viewPager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        Log.d("BeforeLog", "onCreate: Adapter diatur untuk ViewPager2 dan imageList diserahkan ke adapter")

        val dotsImage = Array(imageList.size) { ImageView(this) }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            slideDotLL.addView(it, params)
        }

        Log.d("BeforeLog", "onCreate: Dots untuk navigasi ditambahkan ke slideDotLL")

        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)
        Log.d("BeforeLog", "onCreate: Dot pertama disetel sebagai aktif")

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("BeforeLog", "onPageSelected: Halaman dipilih ke posisi $position")

                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(R.drawable.active_dot)
                    } else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        viewPager2.registerOnPageChangeCallback(pageChangeListener)

        Log.d("BeforeLog", "onCreate: pageChangeListener didaftarkan untuk ViewPager2")

        // Pastikan ID button benar
        val button = findViewById<Button>(R.id.LoginButton)
        button.setOnClickListener {
            Log.d("BeforeLog", "LoginButton diklik, memulai aktivitas LoginApp")
            val intent = Intent(this, LoginApp::class.java)
            startActivity(intent)
        }

        Log.d("BeforeLog", "onCreate: Listener untuk LoginButton diatur")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
        Log.d("BeforeLog", "onDestroy: pageChangeListener dihapus dari ViewPager2")
    }
}
