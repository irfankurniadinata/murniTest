package com.test.murni.ui.home

import androidx.lifecycle.viewModelScope
import com.test.murni.core.BaseViewModel
import com.test.murni.data.model.Article
import com.test.murni.domain.usecase.article.GetArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getArticleUseCase: GetArticleUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Init)
    val state: StateFlow<HomeViewState> get() = _state

    fun getArticle() {
        viewModelScope.launch {
            getArticleUseCase.execute()
                .onStart { showLoading() }
                .catch { e ->
                    hideLoading()
                    onShowMessage(error = e.message.toString())
                }
                .collect { result ->
                    onShowArticle(result)
                    hideLoading()
                }
        }
    }

    private fun onShowArticle(result: List<Article>) {
        _state.value = HomeViewState.ShowArticles(result)
    }

    private fun onShowMessage(error: String) {
        if (error.isNotEmpty()) {
            _state.value = HomeViewState.ShowMessage(message = error)
        }
    }

    private fun hideLoading() {
        _state.value = HomeViewState.Progress(isLoading = false)
    }

    private fun showLoading() {
        _state.value = HomeViewState.Progress(isLoading = true)
    }
}