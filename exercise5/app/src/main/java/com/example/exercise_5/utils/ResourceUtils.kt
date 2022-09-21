package com.example.exercise_5.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

object ResourceUtils {
    fun getDrawable(context: Context, drawable: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, drawable, null)
    }
}