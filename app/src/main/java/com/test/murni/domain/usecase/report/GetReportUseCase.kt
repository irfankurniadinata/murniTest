package com.test.murni.domain.usecase.report

import com.test.murni.data.model.Article
import com.test.murni.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow

class GetReportUseCase(private val repository: ReportRepository) {
    suspend fun execute(): Flow<List<Article>> {
        return repository.getReport()
    }
}