package com.test.murni.ui.dashboard

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.murni.R
import com.test.murni.core.BaseFragment
import com.test.murni.data.model.Article
import com.test.murni.databinding.FragmentDashboardBinding
import com.test.murni.ui.activity.detail_blog.DetailBlogActivity
import com.test.murni.ui.adapter.AdapterClickListener
import com.test.murni.ui.adapter.ArticleAdapter
import com.test.murni.ui.adapter.BlogAdapter
import com.test.murni.utils.K
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel by viewModel<DashboardViewModel>()

    override fun getResLayoutId(): Int = R.layout.fragment_dashboard

    private var blogAdapter = BlogAdapter(arrayListOf()).apply {
        listener = object : AdapterClickListener<Article> {
            override fun onItemClick(data: Article) {
                val intent = Intent(context, DetailBlogActivity::class.java)
                intent.putExtra(K.KEY_ID, data.id)
                startActivity(intent)
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

            blogAdapter.maxWidth = 0
            val layoutManager = GridLayoutManager(context, 2)
            rvBlog.adapter = blogAdapter
            rvBlog.layoutManager = layoutManager

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

    private fun handleFlowState(state: DashBoardViewState) {
        when (state) {
            is DashBoardViewState.Init -> Unit
            is DashBoardViewState.Progress -> onProgress(state.isLoading)
            is DashBoardViewState.ShowMessage -> onShowMessage(state.message)
            is DashBoardViewState.ShowBlog -> onShowBlog(state.blog)
        }
    }

    private fun onShowBlog(blog: List<Article>) {
        blogAdapter.setData(blog)
    }

    private fun onShowMessage(message: String) {
        showToastInfo(message)
    }

    private fun onProgress(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }
}