package com.test.murni.core

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.test.murni.custom.DialogProgress
import com.test.murni.custom.ToastMsg

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    /**
     * This variable is used for showing toast error internet, 500, etc
     */
    protected lateinit var toast: ToastMsg

    /**
     * This variable is used for binding the view
     */
    protected lateinit var binding: B

    /**
     * This function is used for set the view layout
     */
    @LayoutRes
    protected abstract fun getResLayoutId(): Int

    private lateinit var progressDialog : DialogProgress

    /**
     * This function is used for set the action when the view was created
     */
    protected abstract fun onViewCreated()

    /**
     * This function is used for init all function when fragment is created
     * There're have function for binding the view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<B>(inflater, getResLayoutId(), container, false)
            .apply {
                lifecycleOwner = this@BaseFragment
            }

        activity?.let {
            toast = ToastMsg(it)
        }

        activity?.let {
            progressDialog = DialogProgress(it)
        }

        onViewCreated()

        return binding.root
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

    override fun onPause() {
        super.onPause()
        hideToast()
    }
}