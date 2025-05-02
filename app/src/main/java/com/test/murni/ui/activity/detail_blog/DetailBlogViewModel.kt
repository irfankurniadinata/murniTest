package com.test.murni.ui.activity.detail_blog

import androidx.lifecycle.viewModelScope
import com.test.murni.core.BaseViewModel
import com.test.murni.data.model.Article
import com.test.murni.domain.usecase.blog.GetBlogDetailUseCase
import com.test.murni.ui.dashboard.DashBoardViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailBlogViewModel(
    private var getBlogDetailUseCase: GetBlogDetailUseCase
): BaseViewModel() {
    private val _state = MutableStateFlow<DetailBlogViewState>(DetailBlogViewState.Init)
    val state: StateFlow<DetailBlogViewState> get() = _state

    var id: Int? = 0

    fun getBlog() {
        viewModelScope.launch {
            getBlogDetailUseCase.execute(id)
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
        _state.value = DetailBlogViewState.ShowBlog(result)
    }

    private fun onShowMessage(error: String) {
        if (error.isNotEmpty()) {
            _state.value = DetailBlogViewState.ShowMessage(message = error)
        }
    }

    private fun hideLoading() {
        _state.value = DetailBlogViewState.Progress(isLoading = false)
    }

    private fun showLoading() {
        _state.value = DetailBlogViewState.Progress(isLoading = true)
    }
}