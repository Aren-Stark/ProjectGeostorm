package com.adwardtheatre.yan
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object CommonUtils {

    fun stringConvertDouble(s: String): Double {
        val s1 = 0.00
        try {
            return java.lang.Double.parseDouble(s)
        } catch (e: NumberFormatException) {
            // p did not contain a valid double
            Log.d("TAG_DOUBLE", "wrong")
        }

        return s1
    }
}


object ImageUtils {

    fun getBitmapDescriptor(@DrawableRes id: Int, context: Context): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}