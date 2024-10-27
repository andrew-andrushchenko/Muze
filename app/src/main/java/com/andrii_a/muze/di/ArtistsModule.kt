package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.ArtistsRepositoryImpl
import com.andrii_a.muze.data.service.ArtistsService
import com.andrii_a.muze.data.util.BASE_URL
import com.andrii_a.muze.domain.repository.ArtistsRepository
import com.andrii_a.muze.ui.artist_detail.ArtistDetailViewModel
import com.andrii_a.muze.ui.artists.ArtistsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

private fun provideArtistsService(retrofitBuilder: Retrofit.Builder): ArtistsService =
    retrofitBuilder.baseUrl(BASE_URL).build().create(ArtistsService::class.java)

val artistsModule = module {
    single<ArtistsService> { provideArtistsService(get()) }

    singleOf(::ArtistsRepositoryImpl) { bind<ArtistsRepository>() }

    viewModelOf(::ArtistsViewModel)
    viewModelOf(::ArtistDetailViewModel)
}