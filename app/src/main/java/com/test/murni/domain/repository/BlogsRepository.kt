package com.test.murni.domain.repository

import com.test.murni.data.model.Article
import kotlinx.coroutines.flow.Flow

interface BlogsRepository {
    suspend fun getBlog(): Flow<List<Article>>
}