package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.ArtistsRepositoryImpl
import com.andrii_a.muze.data.service.ArtistsService
import com.andrii_a.muze.data.util.BASE_URL
import com.andrii_a.muze.domain.repository.ArtistsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArtistsModule {

    @Provides
    @Singleton
    fun provideArtistsService(retrofitBuilder: Retrofit.Builder): ArtistsService =
        retrofitBuilder.baseUrl(BASE_URL).build().create(ArtistsService::class.java)

    @Provides
    @Singleton
    fun provideArtistsRepository(artistsService: ArtistsService): ArtistsRepository =
        ArtistsRepositoryImpl(artistsService)

}