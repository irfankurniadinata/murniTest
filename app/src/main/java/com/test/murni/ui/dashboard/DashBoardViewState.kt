package com.test.murni.ui.dashboard

import com.test.murni.data.model.Article
import com.test.murni.ui.home.HomeViewState

sealed class DashBoardViewState {
    object Init: DashBoardViewState()
    data class Progress(val isLoading: Boolean) : DashBoardViewState()
    data class ShowMessage(val message: String) : DashBoardViewState()
    data class ShowBlog(val blog: List<Article>) : DashBoardViewState()
}