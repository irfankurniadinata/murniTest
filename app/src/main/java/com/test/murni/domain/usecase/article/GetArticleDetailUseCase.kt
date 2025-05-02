package com.test.murni.domain.usecase.article

import com.test.murni.data.model.Article
import com.test.murni.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleDetailUseCase(private val repository: ArticleRepository) {
    suspend fun execute(id: Int?): Flow<Article> {
        return repository.getArticleDetail(id)
    }
}