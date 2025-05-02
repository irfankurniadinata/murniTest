package com.test.murni.ui.activity.detail_article

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.test.murni.R
import com.test.murni.core.BaseActivity
import com.test.murni.data.model.Article
import com.test.murni.databinding.ActivityDetailArticleBinding
import com.test.murni.ui.custom.NavigationView
import com.test.murni.utils.K
import com.test.murni.utils.extention.setImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArticleActivity : BaseActivity<ActivityDetailArticleBinding>() {

    override fun getResLayoutId(): Int = R.layout.activity_detail_article

    private val viewModel by viewModel<DetailArticleViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        subscribeState()
    }

    private fun initView() {
        binding.apply {
            data = viewModel

            NavigationView(this@DetailArticleActivity).setupNavigationWithTitle("Detail Article") {
                onBackPressed()
            }

            viewModel.id = intent.getIntExtra(K.KEY_ID, 0)
            viewModel.getArticle()
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

    private fun handleFlowState(state: DetailArticleViewState) {
        when (state) {
            is DetailArticleViewState.Init -> Unit
            is DetailArticleViewState.Progress -> onProgress(state.isLoading)
            is DetailArticleViewState.ShowMessage -> onShowMessage(state.message)
            is DetailArticleViewState.ShowArticles -> onShowArticles(state.articles)
        }
    }

    private fun onShowArticles(articles: Article) {
        binding.apply {
            ivArticle.setImage(articles.imageUrl)
            tvArticleName.text = articles.title
            tvNewsSite.text = "News Site : ${articles.newsSite}"
            tvDescription.text = articles.summary
        }
    }

    private fun onShowMessage(message: String) {
        showToastInfo(message)
    }

    private fun onProgress(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }
}