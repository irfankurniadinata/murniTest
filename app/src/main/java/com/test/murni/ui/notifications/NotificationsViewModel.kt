package com.test.murni.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.murni.core.BaseViewModel
import com.test.murni.data.model.Article
import com.test.murni.domain.usecase.report.GetReportUseCase
import com.test.murni.ui.dashboard.DashBoardViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NotificationsViewModel(
    var getReportUseCase: GetReportUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow<NotificationViewState>(NotificationViewState.Init)
    val state: StateFlow<NotificationViewState> get() = _state

    fun getBlog() {
        viewModelScope.launch {
            getReportUseCase.execute()
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
        _state.value = NotificationViewState.ShowReport(result)
    }

    private fun onShowMessage(error: String) {
        if (error.isNotEmpty()) {
            _state.value = NotificationViewState.ShowMessage(message = error)
        }
    }

    private fun hideLoading() {
        _state.value = NotificationViewState.Progress(isLoading = false)
    }

    private fun showLoading() {
        _state.value = NotificationViewState.Progress(isLoading = true)
    }
}