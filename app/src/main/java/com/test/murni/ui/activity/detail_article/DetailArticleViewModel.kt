package com.test.murni.ui.activity.detail_article

import androidx.lifecycle.viewModelScope
import com.test.murni.core.BaseViewModel
import com.test.murni.data.model.Article
import com.test.murni.domain.usecase.article.GetArticleDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailArticleViewModel(
    private var getArticleDetailUseCase: GetArticleDetailUseCase
): BaseViewModel() {
    private val _state = MutableStateFlow<DetailArticleViewState>(DetailArticleViewState.Init)
    val state: StateFlow<DetailArticleViewState> get() = _state

    var id: Int? = 0

    fun getArticle() {
        viewModelScope.launch {
            getArticleDetailUseCase.execute(id)
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

    private fun onShowArticle(result: Article) {
        _state.value = DetailArticleViewState.ShowArticles(result)
    }

    private fun onShowMessage(error: String) {
        if (error.isNotEmpty()) {
            _state.value = DetailArticleViewState.ShowMessage(message = error)
        }
    }

    private fun hideLoading() {
        _state.value = DetailArticleViewState.Progress(isLoading = false)
    }

    private fun showLoading() {
        _state.value = DetailArticleViewState.Progress(isLoading = true)
    }
}