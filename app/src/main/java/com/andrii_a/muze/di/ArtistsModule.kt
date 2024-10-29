package com.andrii_a.muze.di

import com.andrii_a.muze.data.repository.ArtistsRepositoryImpl
import com.andrii_a.muze.data.service.ArtistsService
import com.andrii_a.muze.data.service.ArtistsServiceImpl
import com.andrii_a.muze.domain.repository.ArtistsRepository
import com.andrii_a.muze.ui.artist_detail.ArtistDetailViewModel
import com.andrii_a.muze.ui.artists.ArtistsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val artistsModule = module {
    singleOf(::ArtistsServiceImpl) { bind<ArtistsService>() }

    singleOf(::ArtistsRepositoryImpl) { bind<ArtistsRepository>() }

    viewModelOf(::ArtistsViewModel)
    viewModelOf(::ArtistDetailViewModel)
}