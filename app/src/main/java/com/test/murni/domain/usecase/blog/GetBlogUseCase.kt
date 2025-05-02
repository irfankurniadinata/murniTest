package com.test.murni.domain.usecase.blog

import com.test.murni.data.model.Article
import com.test.murni.domain.repository.ArticleRepository
import com.test.murni.domain.repository.BlogsRepository
import kotlinx.coroutines.flow.Flow

class GetBlogUseCase(private val repository: BlogsRepository) {
    suspend fun execute(): Flow<List<Article>> {
        return repository.getBlog()
    }
}