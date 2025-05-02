package com.test.murni.ui.dashboard

import androidx.lifecycle.viewModelScope
import com.test.murni.core.BaseViewModel
import com.test.murni.data.model.Article
import com.test.murni.domain.usecase.blog.GetBlogUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DashboardViewModel(
    private var getBlogUseCase: GetBlogUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<DashBoardViewState>(DashBoardViewState.Init)
    val state: StateFlow<DashBoardViewState> get() = _state

    fun getBlog() {
        viewModelScope.launch {
            getBlogUseCase.execute()
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
        _state.value = DashBoardViewState.ShowBlog(result)
    }

    private fun onShowMessage(error: String) {
        if (error.isNotEmpty()) {
            _state.value = DashBoardViewState.ShowMessage(message = error)
        }
    }

    private fun hideLoading() {
        _state.value = DashBoardViewState.Progress(isLoading = false)
    }

    private fun showLoading() {
        _state.value = DashBoardViewState.Progress(isLoading = true)
    }
}