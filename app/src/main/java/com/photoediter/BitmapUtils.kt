package com.photoediter

import android.graphics.Bitmap
import android.graphics.BitmapFactory


object BitmapUtils {
    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        return if (byteArray == null || byteArray.size == 0) {
            null
        } else BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}