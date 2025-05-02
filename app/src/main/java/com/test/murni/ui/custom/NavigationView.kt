package com.test.murni.ui.custom

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.test.murni.R
import com.test.murni.utils.extention.convertDpToPixel
import com.test.murni.utils.extention.visible

class NavigationView {
    var activity: Activity
    var view: View

    constructor(activity: Activity){
        this.activity = activity
        this.view = activity.window.decorView.rootView
    }

    constructor(activity: Activity, view: View){
        this.activity = activity
        this.view = view
    }

    constructor(view: View){
        this.view = view
        this.activity = view.context as Activity
    }

    var navigationBack: ImageView? = null
    var navigationTitle: TextView? = null

    fun setupNavigationWithTitle(title: String, callback: (Any) -> Unit): NavigationView {
        navigationBack = view.findViewById<ImageView>(R.id.nav_back)?.apply {
            setOnClickListener {
                callback.invoke(it)
            }
        }
        navigationTitle = view.findViewById<TextView>(R.id.nav_title)?.apply {
            visible()
            text = title
        }
        return this
    }
}