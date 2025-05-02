package com.test.murni.ui.home

import com.test.murni.data.model.Article

sealed class HomeViewState {
    object Init: HomeViewState()
    data class Progress(val isLoading: Boolean) : HomeViewState()
    data class ShowMessage(val message: String) : HomeViewState()
    data class ShowArticles(val articles: List<Article>) : HomeViewState()
}
