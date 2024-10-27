package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.SearchRepositoryImpl
import com.andrii_a.muze.data.service.SearchService
import com.andrii_a.muze.data.util.BASE_URL
import com.andrii_a.muze.domain.repository.SearchRepository
import com.andrii_a.muze.ui.search.SearchViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

private fun provideSearchService(retrofitBuilder: Retrofit.Builder): SearchService =
    retrofitBuilder.baseUrl(BASE_URL).build().create(SearchService::class.java)

val searchModule = module {
    single<SearchService> { provideSearchService(get()) }

    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    viewModelOf(::SearchViewModel)
}