package com.test.murni.di

import com.test.murni.ui.activity.MainViewModel
import com.test.murni.ui.dashboard.DashboardViewModel
import com.test.murni.ui.home.HomeViewModel
import com.test.murni.ui.notifications.NotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { MainViewModel() }

    viewModel { NotificationsViewModel(get()) }
}