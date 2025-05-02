package com.test.murni.ui.notifications

import com.test.murni.data.model.Article

sealed class NotificationViewState {
    object Init: NotificationViewState()
    data class Progress(val isLoading: Boolean) : NotificationViewState()
    data class ShowMessage(val message: String) : NotificationViewState()
    data class ShowReport(val reports: List<Article>) : NotificationViewState()
}
