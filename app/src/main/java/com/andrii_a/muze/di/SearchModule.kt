package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.SearchRepositoryImpl
import com.andrii_a.muze.data.service.SearchService
import com.andrii_a.muze.data.util.BASE_URL
import com.andrii_a.muze.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchService(retrofitBuilder: Retrofit.Builder): SearchService =
        retrofitBuilder.baseUrl(BASE_URL).build().create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideSearchRepository(searchService: SearchService): SearchRepository =
        SearchRepositoryImpl(searchService)

}