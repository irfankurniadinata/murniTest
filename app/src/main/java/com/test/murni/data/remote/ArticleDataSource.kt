package com.test.murni.data.remote

import com.test.murni.core.BaseResponse
import com.test.murni.data.model.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleDataSource {
    @GET("/v4/articles")
    suspend fun getArticle(): Response<BaseResponse<List<Article>>>

    @GET("/v4/articles/{id}")
    suspend fun getArticleDetail(
        @Path("id") id: String?
    ): Response<BaseResponse<Article>>
}