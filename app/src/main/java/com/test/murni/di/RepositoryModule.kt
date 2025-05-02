package com.test.murni.di

import com.test.murni.data.remote.ArticleDataSource
import com.test.murni.data.remote.BlogDataSource
import com.test.murni.data.remote.ReportDataSource
import com.test.murni.domain.repository.ArticleRepository
import com.test.murni.domain.repository.ArticleRepositoryImpl
import com.test.murni.domain.repository.BlogsRepository
import com.test.murni.domain.repository.BlogsRepositoryImpl
import com.test.murni.domain.repository.ReportRepository
import com.test.murni.domain.repository.ReportRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single { provideArticleDataSource(get())}
    single { provideArticleRepository(get()) }

    single { provideBlogDataSource(get())}
    single { provideBlogRepository(get()) }

    single { provideReportDataSource(get())}
    single { provideReportRepository(get()) }
}

fun provideArticleDataSource(retrofit: Retrofit) : ArticleDataSource {
    return retrofit.create(ArticleDataSource::class.java)
}

fun provideArticleRepository(customerDataSource: ArticleDataSource) : ArticleRepository {
    return ArticleRepositoryImpl(customerDataSource)
}

fun provideBlogDataSource(retrofit: Retrofit) : BlogDataSource {
    return retrofit.create(BlogDataSource::class.java)
}

fun provideBlogRepository(blogDataSource: BlogDataSource) : BlogsRepository {
    return BlogsRepositoryImpl(blogDataSource)
}

fun provideReportDataSource(retrofit: Retrofit) : ReportDataSource {
    return retrofit.create(ReportDataSource::class.java)
}

fun provideReportRepository(reportDataSOurce: ReportDataSource) : ReportRepository {
    return ReportRepositoryImpl(reportDataSOurce)
}