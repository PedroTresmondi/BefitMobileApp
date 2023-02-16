package com.example.befitapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitMapHelper {

    fun vectorToBitMap(
        context: Context,
        @DrawableRes id: Int,
        @ColorInt colorInt: Int
    ) : BitmapDescriptor? {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)

        if (vectorDrawable == null){
            return BitmapDescriptorFactory.defaultMarker()
        }

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, colorInt)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}