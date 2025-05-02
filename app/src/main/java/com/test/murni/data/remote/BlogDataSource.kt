package com.test.murni.data.remote

import com.test.murni.core.BaseResponse
import com.test.murni.data.model.Article
import retrofit2.Response
import retrofit2.http.GET

interface BlogDataSource {
    @GET("/v4/blogs")
    suspend fun getBlog(): Response<BaseResponse<List<Article>>>
}