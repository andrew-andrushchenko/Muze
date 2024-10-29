package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.ArtworksRepositoryImpl
import com.andrii_a.muze.data.service.ArtworksService
import com.andrii_a.muze.data.service.ArtworksServiceImpl
import com.andrii_a.muze.domain.repository.ArtworksRepository
import com.andrii_a.muze.ui.artwork_detail.ArtworkDetailViewModel
import com.andrii_a.muze.ui.artworks.ArtworksViewModel
import com.andrii_a.muze.ui.artworks_by_artist.ArtworksByArtistViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val artworksModule = module {
    singleOf(::ArtworksServiceImpl) { bind<ArtworksService>() }

    singleOf(::ArtworksRepositoryImpl) { bind<ArtworksRepository>() }

    viewModelOf(::ArtworksViewModel)
    viewModelOf(::ArtworkDetailViewModel)
    viewModelOf(::ArtworksByArtistViewModel)
}