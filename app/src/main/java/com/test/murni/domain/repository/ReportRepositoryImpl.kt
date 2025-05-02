package com.test.murni.domain.repository

import com.test.murni.data.model.Article
import com.test.murni.data.remote.ReportDataSource
import com.test.murni.utils.extention.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReportRepositoryImpl(private val reportDataSource: ReportDataSource) : ReportRepository {
    override suspend fun getReport(): Flow<List<Article>> {
        return flow {
            val response = reportDataSource.getReport()
            if (response.isSuccessful) {
                val body = response.body()
                emit(body?.results!!)
            } else {
                val errorBody = response.errorBody().get()
                error(errorBody.message!!)
            }
        }
    }
}