package com.test.murni.utils.extention

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.dpToPixel(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun TextView.setTextHtml(value: String?) {
    if (value == null) return
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(value)
    }
}

fun convertDpToPixel(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

@BindingAdapter("app:loadImage")
fun ImageView.setImage(url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(this)
        .load(url)
        .into(this)
}

@BindingAdapter("app:loadImage")
fun ImageView.setImage(resource: Int?) {
    if (resource == null || resource == 0) return
    Glide.with(this)
        .load(resource)
        .into(this)
}