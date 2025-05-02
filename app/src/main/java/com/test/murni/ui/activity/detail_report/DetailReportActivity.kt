package com.test.murni.ui.activity.detail_report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.test.murni.R
import com.test.murni.core.BaseActivity
import com.test.murni.data.model.Article
import com.test.murni.databinding.ActivityDetailReportBinding
import com.test.murni.ui.activity.detail_blog.DetailBlogViewModel
import com.test.murni.ui.activity.detail_blog.DetailBlogViewState
import com.test.murni.ui.custom.NavigationView
import com.test.murni.utils.K
import com.test.murni.utils.extention.setImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailReportActivity : BaseActivity<ActivityDetailReportBinding>() {

    override fun getResLayoutId(): Int = R.layout.activity_detail_report

    private val viewModel by viewModel<DetailReportViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        subscribeState()
    }

    private fun initView() {
        binding.apply {
            data = viewModel

            NavigationView(this@DetailReportActivity).setupNavigationWithTitle("Detail Report") {
                onBackPressed()
            }

            viewModel.id = intent.getIntExtra(K.KEY_ID, 0)
            viewModel.getReport()
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

    private fun handleFlowState(state: DetailReportViewState) {
        when (state) {
            is DetailReportViewState.Init -> Unit
            is DetailReportViewState.Progress -> onProgress(state.isLoading)
            is DetailReportViewState.ShowMessage -> onShowMessage(state.message)
            is DetailReportViewState.ShowReport -> onShowReport(state.report)
        }
    }

    private fun onShowReport(report: Article) {
        binding.apply {
            ivReport.setImage(report.imageUrl)
            tvReportName.text = report.title
            tvNewsSite.text = "News Site : ${report.newsSite}"
            tvDescription.text = report.summary
        }
    }

    private fun onShowMessage(message: String) {
        showToastInfo(message)
    }

    private fun onProgress(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }
}