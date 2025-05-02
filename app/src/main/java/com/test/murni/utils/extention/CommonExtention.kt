package com.test.murni.utils.extention

import com.google.gson.Gson
import com.test.murni.core.ErrorResponse
import okhttp3.ResponseBody

fun ResponseBody?.get(): ErrorResponse {
    try {
        return Gson().fromJson(this?.charStream(), ErrorResponse::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ErrorResponse(
        code = 500,
        message = "Telah terjadi kesalahan"
    )
}