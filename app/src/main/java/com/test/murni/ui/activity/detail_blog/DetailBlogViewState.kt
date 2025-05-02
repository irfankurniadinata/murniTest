package com.test.murni.ui.activity.detail_blog

import com.test.murni.data.model.Article

sealed class DetailBlogViewState {
    object Init: DetailBlogViewState()
    data class Progress(val isLoading: Boolean) : DetailBlogViewState()
    data class ShowMessage(val message: String) : DetailBlogViewState()
    data class ShowBlog(val blog: Article) : DetailBlogViewState()
}
