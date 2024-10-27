package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.ArtworksRepositoryImpl
import com.andrii_a.muze.data.service.ArtworksService
import com.andrii_a.muze.data.util.BASE_URL
import com.andrii_a.muze.domain.repository.ArtworksRepository
import com.andrii_a.muze.ui.artwork_detail.ArtworkDetailViewModel
import com.andrii_a.muze.ui.artworks.ArtworksViewModel
import com.andrii_a.muze.ui.artworks_by_artist.ArtworksByArtistViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

private fun provideArtworksService(retrofitBuilder: Retrofit.Builder): ArtworksService =
    retrofitBuilder.baseUrl(BASE_URL).build().create(ArtworksService::class.java)

val artworksModule = module {
    single<ArtworksService> { provideArtworksService(get()) }

    singleOf(::ArtworksRepositoryImpl) { bind<ArtworksRepository>() }

    viewModelOf(::ArtworksViewModel)
    viewModelOf(::ArtworkDetailViewModel)
    viewModelOf(::ArtworksByArtistViewModel)
}