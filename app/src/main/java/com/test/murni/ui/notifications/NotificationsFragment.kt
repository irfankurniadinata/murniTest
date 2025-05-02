package com.test.murni.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.test.murni.R
import com.test.murni.core.BaseFragment
import com.test.murni.data.model.Article
import com.test.murni.databinding.FragmentNotificationsBinding
import com.test.murni.ui.activity.detail_report.DetailReportActivity
import com.test.murni.ui.adapter.AdapterClickListener
import com.test.murni.ui.adapter.ArticleAdapter
import com.test.murni.ui.adapter.ReportAdapter
import com.test.murni.ui.dashboard.DashBoardViewState
import com.test.murni.utils.K
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel by viewModel<NotificationsViewModel>()

    override fun getResLayoutId(): Int = R.layout.fragment_notifications

    private var reportAdapter = ReportAdapter(arrayListOf()).apply {
        listener = object : AdapterClickListener<Article> {
            override fun onItemClick(data: Article) {
                val intent = Intent(context, DetailReportActivity::class.java)
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

            reportAdapter.maxWidth = 0
            val layoutManager = GridLayoutManager(context, 2)
            rvReport.adapter = reportAdapter
            rvReport.layoutManager = layoutManager

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

    private fun handleFlowState(state: NotificationViewState) {
        when (state) {
            is NotificationViewState.Init -> Unit
            is NotificationViewState.Progress -> onProgress(state.isLoading)
            is NotificationViewState.ShowMessage -> onShowMessage(state.message)
            is NotificationViewState.ShowReport -> onShowBlog(state.reports)
        }
    }

    private fun onShowBlog(reports: List<Article>) {
        reportAdapter.setData(reports)
    }

    private fun onShowMessage(message: String) {
        showToastInfo(message)
    }

    private fun onProgress(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }
}