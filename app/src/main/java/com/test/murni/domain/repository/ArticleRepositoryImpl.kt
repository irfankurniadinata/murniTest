package com.test.murni.domain.repository

import com.test.murni.data.model.Article
import com.test.murni.data.remote.ArticleDataSource
import com.test.murni.utils.extention.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArticleRepositoryImpl(private val articleDataSource: ArticleDataSource) : ArticleRepository {
    override suspend fun getArticle(): Flow<List<Article>> {
        return flow {
            val response = articleDataSource.getArticle()
            if (response.isSuccessful) {
                val body = response.body()
                emit(body?.results!!)
            } else {
                val errorBody = response.errorBody().get()
                error(errorBody.message!!)
            }
        }
    }

    override suspend fun getArticleDetail(id: Int?): Flow<Article> {
        return flow {
            val response = articleDataSource.getArticleDetail(id)
            if (response.isSuccessful) {
                val body = response.body()
                emit(body!!)
            } else {
                val errorBody = response.errorBody().get()
                error(errorBody.message!!)
            }
        }
    }
}