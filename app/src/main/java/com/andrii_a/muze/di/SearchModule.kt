package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.SearchRepositoryImpl
import com.andrii_a.muze.data.service.SearchService
import com.andrii_a.muze.data.service.SearchServiceImpl
import com.andrii_a.muze.domain.repository.SearchRepository
import com.andrii_a.muze.ui.search.SearchViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    singleOf(::SearchServiceImpl) { bind<SearchService>() }

    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    viewModelOf(::SearchViewModel)
}