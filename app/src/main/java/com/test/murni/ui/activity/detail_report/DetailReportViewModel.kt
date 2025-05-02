package com.test.murni.ui.activity.detail_report

import androidx.lifecycle.viewModelScope
import com.test.murni.core.BaseViewModel
import com.test.murni.data.model.Article
import com.test.murni.domain.usecase.report.GetReportDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailReportViewModel(
    private val getReportDetailUseCase: GetReportDetailUseCase
): BaseViewModel() {
    private val _state = MutableStateFlow<DetailReportViewState>(DetailReportViewState.Init)
    val state: StateFlow<DetailReportViewState> get() = _state

    var id: Int? = 0

    fun getReport() {
        viewModelScope.launch {
            getReportDetailUseCase.execute(id)
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
        _state.value = DetailReportViewState.ShowReport(result)
    }

    private fun onShowMessage(error: String) {
        if (error.isNotEmpty()) {
            _state.value = DetailReportViewState.ShowMessage(message = error)
        }
    }

    private fun hideLoading() {
        _state.value = DetailReportViewState.Progress(isLoading = false)
    }

    private fun showLoading() {
        _state.value = DetailReportViewState.Progress(isLoading = true)
    }
}