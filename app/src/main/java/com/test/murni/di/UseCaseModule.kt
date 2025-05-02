package com.test.murni.di

import com.test.murni.domain.usecase.article.GetArticleUseCase
import com.test.murni.domain.usecase.blog.GetBlogUseCase
import com.test.murni.domain.usecase.report.GetReportUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetArticleUseCase(get()) }
    single { GetBlogUseCase(get()) }
    single { GetReportUseCase(get()) }
}