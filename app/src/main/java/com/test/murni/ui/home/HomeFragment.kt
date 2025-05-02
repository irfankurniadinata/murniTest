package com.test.murni.ui.home

import android.util.Log
import android.view.View
import com.test.murni.R
import com.test.murni.core.BaseFragment
import com.test.murni.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.murni.data.model.Article
import com.test.murni.ui.adapter.AdapterClickListener
import com.test.murni.ui.adapter.ArticleAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getResLayoutId(): Int = R.layout.fragment_home

    private val viewModel by viewModel<HomeViewModel>()

    private var articleAdapter = ArticleAdapter(arrayListOf()).apply {
        listener = object : AdapterClickListener<Article> {
            override fun onItemClick(data: Article) {

            }

            override fun onViewClick(view: View, data: Article) {

            }
        }
    }

    override fun onViewCreated() {
        initView()
        subscribeState()
    }

    private fun initView() {
        binding.apply {
            data = viewModel

            articleAdapter.maxWidth = 0
            val layoutManager = GridLayoutManager(context, 2)
            rvArticle.adapter = articleAdapter
            rvArticle.layoutManager = layoutManager

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

    private fun handleFlowState(state: HomeViewState) {
        when (state) {
            is HomeViewState.Init -> Unit
            is HomeViewState.Progress -> onProgress(state.isLoading)
            is HomeViewState.ShowMessage -> onShowMessage(state.message)
            is HomeViewState.ShowArticles -> onShowArticles(state.articles)
        }
    }

    private fun onShowArticles(articles: List<Article>) {
        articleAdapter.setData(articles)
    }

    private fun onShowMessage(message: String) {
        showToastInfo(message)
    }

    private fun onProgress(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }
}