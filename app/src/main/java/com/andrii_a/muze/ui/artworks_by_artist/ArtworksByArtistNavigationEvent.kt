package com.andrii_a.muze.ui.artworks_by_artist

sealed interface ArtworksByArtistNavigationEvent {
    data object NavigateBack : ArtworksByArtistNavigationEvent

    data class NavigateToArtworkDetail(val artworkId: Int) : ArtworksByArtistNavigationEvent
}