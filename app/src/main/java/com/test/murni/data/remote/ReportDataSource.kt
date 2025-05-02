package com.test.murni.data.remote

import com.test.murni.core.BaseResponse
import com.test.murni.data.model.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReportDataSource {
    @GET("/v4/reports")
    suspend fun getReport(): Response<BaseResponse<List<Article>>>

    @GET("/v4/reports/{id}")
    suspend fun getReportDetail(
        @Path("id") id: Int?
    ): Response<Article>
}