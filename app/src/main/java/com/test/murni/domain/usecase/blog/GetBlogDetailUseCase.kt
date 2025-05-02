package com.test.murni.domain.usecase.blog

import com.test.murni.data.model.Article
import com.test.murni.domain.repository.BlogsRepository
import kotlinx.coroutines.flow.Flow

class GetBlogDetailUseCase(private val repository: BlogsRepository) {
    suspend fun execute(id: Int?): Flow<Article> {
        return repository.getBlogDetail(id)
    }
}