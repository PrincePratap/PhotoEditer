package com.photoediter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.photoediter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST_FROM_GALLARY = 1

    lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)



        binding.ButtonSelect.setOnClickListener {
            val options = arrayOf("Take Photo", "Select from Gallery", "Cancel")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Option")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> checkAndRequestCameraPermission()
                    1 -> openGallery()

                }
            }
            builder.show()

        }


    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_FROM_GALLARY)
    }




    private var uri: Uri? = null





    private val permissionCameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                openCamera()
            }
        }

    private fun checkAndRequestCameraPermission() {
        val isCamera = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (!isCamera) {
            permissionCameraLauncher.launch(android.Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }



    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            try {
                val i = Intent(this,EditerActivity3::class.java)
                i.putExtra("myImage",uri.toString())
                startActivity(i)

            } catch (e: Exception) {


            }

        }
    }


    private fun openCamera() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis())
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis())
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/" + "png");
        contentValues.put(
            MediaStore.Images.Media.RELATIVE_PATH,
            Environment.DIRECTORY_PICTURES + "/DIGI-VAHAN"
        )
        uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        cameraLauncher.launch(uri)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST_FROM_GALLARY) {
                val uri: Uri = data?.data!!
                val i = Intent(this, EditerActivity3::class.java)
                i.putExtra("myImage", uri.toString())
                startActivity(i)


            }

        }
    }


}





