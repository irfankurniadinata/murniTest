package com.test.murni.domain.repository

import com.test.murni.data.model.Article
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun getReport(): Flow<List<Article>>

    suspend fun getReportDetail(id: Int?): Flow<Article>
}