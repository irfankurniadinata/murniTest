package com.test.murni.ui.activity.detail_blog

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.test.murni.R
import com.test.murni.core.BaseActivity
import com.test.murni.data.model.Article
import com.test.murni.databinding.ActivityDetailBlogBinding
import com.test.murni.ui.custom.NavigationView
import com.test.murni.utils.K
import com.test.murni.utils.extention.setImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailBlogActivity : BaseActivity<ActivityDetailBlogBinding>() {

    override fun getResLayoutId(): Int = R.layout.activity_detail_blog

    private val viewModel by viewModel<DetailBlogViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        subscribeState()
    }

    private fun initView() {
        binding.apply {
            data = viewModel

            NavigationView(this@DetailBlogActivity).setupNavigationWithTitle("Detail Blog") {
                onBackPressed()
            }

            viewModel.id = intent.getIntExtra(K.KEY_ID, 0)
            viewModel.getBlog()
        }
    }

    private fun subscribeState() {
        lifecycleScope.launch {
            viewModel.state
                .onEach { state ->
                    handleFlowState(state)
                }
                .launchIn(this)
        }
    }

    private fun handleFlowState(state: DetailBlogViewState) {
        when (state) {
            is DetailBlogViewState.Init -> Unit
            is DetailBlogViewState.Progress -> onProgress(state.isLoading)
            is DetailBlogViewState.ShowMessage -> onShowMessage(state.message)
            is DetailBlogViewState.ShowBlog -> onShowBlog(state.blog)
        }
    }

    private fun onShowBlog(blog: Article) {
        binding.apply {
            ivBlog.setImage(blog.imageUrl)
            tvBlogName.text = blog.title
            tvNewsSite.text = "News Site : ${blog.newsSite}"
            tvDescription.text = blog.summary
        }
    }

    private fun onShowMessage(message: String) {
        showToastInfo(message)
    }

    private fun onProgress(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }
}