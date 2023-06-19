package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.ArtworksRepositoryImpl
import com.andrii_a.muze.data.service.ArtworksService
import com.andrii_a.muze.data.util.BASE_URL
import com.andrii_a.muze.domain.repository.ArtworksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArtworksModule {

    @Provides
    @Singleton
    fun provideArtworksService(retrofitBuilder: Retrofit.Builder): ArtworksService =
        retrofitBuilder.baseUrl(BASE_URL).build().create(ArtworksService::class.java)

    @Provides
    @Singleton
    fun provideArtworksRepository(artworksService: ArtworksService): ArtworksRepository =
        ArtworksRepositoryImpl(artworksService)

}