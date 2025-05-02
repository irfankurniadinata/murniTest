package com.test.murni.core

import android.app.Application
import android.content.Context
import com.test.murni.di.networkModule
import com.test.murni.di.repositoryModule
import com.test.murni.di.useCaseModule
import com.test.murni.di.viewModelModule
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        Paper.init(this)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    companion object {
        private var mInstance: App? = null

        fun getContext(): Context? {
            return mInstance?.applicationContext
        }
    }
}