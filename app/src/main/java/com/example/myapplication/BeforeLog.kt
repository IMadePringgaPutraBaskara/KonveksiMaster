package com.example.myapplication

import android.content.Intent
import android.os.Bundle
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

        viewPager2 = findViewById(R.id.ViewPager2)
        val slideDotLL = findViewById<LinearLayout>(R.id.slideDotLL)

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

        val imageAdapter = ImageAdapter()
        viewPager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        val dotsImage = Array(imageList.size) { ImageView(this) }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            slideDotLL.addView(it, params)
        }

        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
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

        // Pastikan ID button benar
        val button = findViewById<Button>(R.id.LoginButton)
        button.setOnClickListener {
            // Menggunakan BeforeLog::class.java untuk intent
            val intent = Intent(this, LoginApp::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)

    }


}
