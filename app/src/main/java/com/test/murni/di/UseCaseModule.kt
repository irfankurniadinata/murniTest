package com.test.murni.di

import com.test.murni.domain.usecase.article.GetArticleDetailUseCase
import com.test.murni.domain.usecase.article.GetArticleUseCase
import com.test.murni.domain.usecase.blog.GetBlogDetailUseCase
import com.test.murni.domain.usecase.blog.GetBlogUseCase
import com.test.murni.domain.usecase.report.GetReportDetailUseCase
import com.test.murni.domain.usecase.report.GetReportUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetArticleUseCase(get()) }
    single { GetArticleDetailUseCase(get()) }
    single { GetBlogUseCase(get()) }
    single { GetBlogDetailUseCase(get()) }
    single { GetReportUseCase(get()) }
    single { GetReportDetailUseCase(get()) }

}