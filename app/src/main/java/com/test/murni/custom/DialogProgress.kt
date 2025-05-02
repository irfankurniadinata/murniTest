package com.test.murni.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.test.murni.R

class DialogProgress(context : Context) : Dialog(context){

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        imageView = findViewById(R.id.imageView)
        // Load animation
        val animation = AnimationUtils.loadAnimation(context, R.anim.scale_animation)

        // Apply animation to imageView
        imageView.startAnimation(animation)
    }

}