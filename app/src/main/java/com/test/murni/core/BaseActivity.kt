package com.test.murni.core

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.test.murni.R
import com.test.murni.custom.DialogProgress
import com.test.murni.custom.ToastMsg
import com.test.murni.databinding.NavBarLayoutBinding

abstract class BaseActivity <B : ViewDataBinding> : AppCompatActivity() {
    /**
     * This variable is used for binding the view
     */
    protected lateinit var binding: B

    /**
     * This function is used for set the view layout
     */
    @LayoutRes
    protected abstract fun getResLayoutId(): Int

    /**
     * This function is used for default progress dialog
     */
    private lateinit var progressDialog: DialogProgress

    /**
     * This function is used for default progress dialog
     */
    private lateinit var toast: ToastMsg

    /**
     * This variabel is used for default progress dialog
     */
    private var navBar: NavBarLayoutBinding? = null

    /**
     * This function is used for set the action when the activity was created
     */
    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    /**
     * This function is used for init all function when activity is created
     * There're have function for binding the view with data binding
     * There're also have function for listen the connection state change
     * and have function to observe the error state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
        binding = DataBindingUtil.setContentView<B>(this, getResLayoutId())
            .apply {
                lifecycleOwner = this@BaseActivity
            }

        progressDialog = DialogProgress(this)
        toast = ToastMsg(this)

        onActivityCreated(savedInstanceState)
    }

    /*
    *  Example for setup NavBar Menu
    *  with(binding.navBar){
    *      setupNavbar(this, "NavBar title") // default back icon
    *                    or
    *      setupNavbar(this, "NavBar title", R.drawable.navbar_icon) // custom back icon
    *  }
    * */

    fun setupNavbar(navBar: NavBarLayoutBinding, title: String? = null, iconBack: Int? = null) {
        this.navBar = navBar

        navBar.navBack.setOnClickListener {
            onBackPressed()
        }

        iconBack?.let {
            navBar.navBack.setImageResource(it)
        }

        navBar.navTitle.text = title
    }

    fun setProgress(boolean: Boolean) {
        if (boolean) showProgress()
        else hideProgress()
    }

    fun showProgress(): DialogProgress {
        try {
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return progressDialog
    }

    fun hideProgress() {
        try {
            progressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showMessage(info: String?, error: String?) {
        info?.let {
            showToastError(info)
        }
        error?.let {
            showToastError(error)
        }
    }

    fun showToastInfo(message: String?) {
        toast.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                setDismiss(ToastMsg.LENGTH_LONG)
                showInfo(message)
            }, 200)
        }
    }

    fun showToastInfo(message: String?, callback : ToastMsg.Callback) {
        toast.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                setDismiss(ToastMsg.LENGTH_LONG, callback = callback)
                showInfo(message)
            }, 200)
        }
    }

    fun showToastError(message: String?) {
        toast.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                setDismiss(ToastMsg.LENGTH_LONG)
                showError(message)
            }, 200)
        }
    }

    fun hideToast() {
        toast.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        toast.dismiss()
    }
}