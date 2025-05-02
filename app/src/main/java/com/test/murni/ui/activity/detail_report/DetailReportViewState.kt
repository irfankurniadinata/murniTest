package com.test.murni.ui.activity.detail_report

import com.test.murni.data.model.Article

sealed class DetailReportViewState {
    object Init: DetailReportViewState()
    data class Progress(val isLoading: Boolean) : DetailReportViewState()
    data class ShowMessage(val message: String) : DetailReportViewState()
    data class ShowReport(val report: Article) : DetailReportViewState()
}
