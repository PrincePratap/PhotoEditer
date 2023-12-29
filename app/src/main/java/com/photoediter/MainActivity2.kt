package com.photoediter

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.opengl.Matrix
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.photoediter.databinding.ActivityMain2Binding
import java.io.ByteArrayOutputStream

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding

    private lateinit var imageView: ImageView
    private lateinit var imageView2 : ImageView
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleGestureDetector2: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f
    private var scaleFactor2 = 1.0f


    private var lastX = 0f
    private var lastY = 0f

    private var lastX2 = 0f
    private var lastY2 = 0f

    private var isMoving = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        val uri1 = intent.getStringExtra("uri1")
        val uri2 = intent.getStringExtra("uri2")

        binding.showImage1.setImageURI(uri1!!.toUri())
        binding.showImage2.setImageURI(uri2!!.toUri())

        imageView = binding.showImage1
        imageView2 = binding.showImage2





        binding.zoomButton.setOnClickListener {
            isMoving = false
        }

        binding.moveButton.setOnClickListener {
            isMoving = true
        }


        val screeShotOFImage = binding.screenShot

        binding.next2Button.setOnClickListener {

            try {
                val screenShot =   takeScreenshotOfView(screeShotOFImage.rootView,screeShotOFImage.height-40,screeShotOFImage.width)
//                val bitmap: Bitmap = screenShot
//
//                val stream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
//                val byteArray = stream.toByteArray()

                val uri = BitToUri.bitmapToUri(this,screenShot)




                val intent = Intent(this, EditerActivity3::class.java)
                intent.putExtra("bitmap", uri.toString())
                startActivity(intent)
            }catch (e:Exception){
                Log.d("ASas",e.message.toString())
            }


        }

                scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
               scaleGestureDetector2 = ScaleGestureDetector(this, ScaleListener2())
        imageView.setOnTouchListener { _, event -> onTouch(event) }
        imageView2.setOnTouchListener{_,event ->onTouch2(event) }

    }
        private fun onTouch(event: MotionEvent):  Boolean{
        scaleGestureDetector?.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMoving) {
                    val deltaX: Float = event.x - lastX
                    val deltaY: Float = event.y - lastY

                    imageView.translationX += deltaX
                    imageView.translationY += deltaY

                    lastX = event.x
                    lastY = event.y
                }
            }
        }

        return true
    }

    private fun onTouch2(event: MotionEvent): Boolean {
        scaleGestureDetector2?.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX2 = event.x
                lastY2 = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMoving) {
                    val deltaX: Float = event.x - lastX2
                    val deltaY: Float = event.y - lastY2

                    imageView2.translationX += deltaX
                    imageView2.translationY += deltaY

                    lastX2 = event.x
                    lastY2 = event.y
                }
            }
        }

        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f))
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor
            return true
        }
    }
    private inner class ScaleListener2 : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor2 *= detector.scaleFactor
            scaleFactor2 = Math.max(0.1f, Math.min(scaleFactor2, 10.0f))
            imageView2.scaleX = scaleFactor2
            imageView2.scaleY = scaleFactor2
            return true
        }
    }
      private  fun takeScreenshotOfView(view: View?, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view?.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view?.draw(canvas)
        return bitmap
    }




}