package com.test.murni.ui.activity.detail_article

import com.test.murni.data.model.Article

sealed class DetailArticleViewState {
    object Init: DetailArticleViewState()
    data class Progress(val isLoading: Boolean) : DetailArticleViewState()
    data class ShowMessage(val message: String) : DetailArticleViewState()
    data class ShowArticles(val articles: Article) : DetailArticleViewState()
}
