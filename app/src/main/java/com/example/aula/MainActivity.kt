package com.example.aula

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.aula.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textStart: TextView = binding.textStart
        val progressBar = binding.progressBar


        textStart.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE


        val listImage = listOf(
            "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
            "https://images.unsplash.com/photo-1519337265831-281ec6cc8514"
        )
        with(binding){
        button.setOnClickListener {
            val randomNumber = Random.nextInt(listImage.size)
            setImage(image, listImage[randomNumber], progressBar, textStart)
        }

        buttonCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }
        }
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // Display error state to the user.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            Glide.with(this)
                .load(imageBitmap)
                .into(binding.image)
            binding.textStart.visibility = View.GONE
        }
    }

    private fun setImage(imageView: ImageView, image: String, progressBar: ProgressBar, textStart: TextView) {
        progressBar.visibility = View.VISIBLE
        textStart.visibility = View.INVISIBLE

        Glide.with(this)
            .load(image)
            .override(200, 200)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE // Ocultar ProgressBar quando a imagem for carregada
                    return false
                }
            })
            .into(imageView)
    }
}
