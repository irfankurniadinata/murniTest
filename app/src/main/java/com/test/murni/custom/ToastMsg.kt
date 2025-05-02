package com.test.murni.custom

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.test.murni.R
import com.test.murni.utils.extention.dpToPixel
import com.test.murni.utils.extention.setTextHtml

class ToastMsg (private val context: Activity) {
    private val popupWindow: PopupWindow?
    private val tvMessage: TextView
    private val tvClose: TextView
    private val toastLayout: LinearLayout

    init {
        val popupView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_toast_message, null)
        tvMessage = popupView.findViewById(R.id.tv_toast_message)
        tvClose = popupView.findViewById(R.id.tv_toast_close)
        toastLayout = popupView.findViewById(R.id.toast_layout)

        tvClose.setOnClickListener {
            dismiss()
        }
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.isFocusable = false
        popupWindow.isOutsideTouchable = false

    }

    companion object {

        const val LENGTH_SHORT = 3000L
        const val LENGTH_LONG = 5000L

        fun make(activity: Activity, message: String, duration: Long): ToastMsg {
            return ToastMsg(activity).setMessage(message).setDismiss(duration)
        }
    }

    fun showError(message: String?) {
        message?.let {
            setDismiss(0)
            tvMessage.setTextHtml(message)
            toastLayout.setBackgroundResource(R.drawable.toast_error)
            context.runOnUiThread {
                show()
            }
        }
    }

    fun showInfo(message: String?) {
        message?.let {
            tvMessage.text = message
            toastLayout.setBackgroundResource(R.drawable.toast_info)
            context.runOnUiThread {
                show()
            }
        }
    }

    fun setDismiss(duration: Long): ToastMsg {
        setDismiss(duration, null)
        return this
    }

    private val handler = Handler(Looper.getMainLooper())
    fun setDismiss(duration: Long, callback: Callback?): ToastMsg {
        handler.removeCallbacksAndMessages(null)
        if (duration > 0) {
            handler.postDelayed({
                dismiss()
                callback?.onCompleted()
            }, duration)
        }
        return this
    }

    fun setMessage(message: String): ToastMsg {
        tvMessage.text = message
        return this
    }

    @SuppressLint("NewApi")
    fun show() {
        try {
            if (context.isDestroyed || context.isFinishing || context.window == null) return
            if (context.window?.isActive == true && popupWindow != null) {
                if (popupWindow.isShowing || popupWindow.isAttachedInDecor) {
                    dismiss()
                }
                popupWindow.showAtLocation(
                    context.window.decorView.rootView, Gravity.BOTTOM, 0,
                    context.dpToPixel(52)
                )

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss() {
        try {
            if (popupWindow != null) {
                if (popupWindow.isShowing) {
                    popupWindow.dismiss()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface Callback {
        fun onCompleted()
    }
}