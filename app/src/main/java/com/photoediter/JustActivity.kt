package com.photoediter

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.photoediter.databinding.ActivityJustBinding

class JustActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJustBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJustBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val byteArray = intent.getByteArrayExtra("bitmap")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)
        binding.justImage.setImageBitmap(bitmap)








    }
}