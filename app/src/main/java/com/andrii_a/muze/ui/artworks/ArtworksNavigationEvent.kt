package com.andrii_a.muze.ui.artworks

sealed interface ArtworksNavigationEvent {
    data class NavigateToArtworkDetail(val artworkId: Int) : ArtworksNavigationEvent
}