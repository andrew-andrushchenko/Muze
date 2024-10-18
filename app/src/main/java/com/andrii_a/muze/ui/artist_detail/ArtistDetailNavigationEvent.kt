package com.andrii_a.muze.ui.artist_detail

sealed interface ArtistDetailNavigationEvent {
    data object NavigateBack : ArtistDetailNavigationEvent

    data class NavigateToArtistArtworks(val artistId: Int) : ArtistDetailNavigationEvent

    data class NavigateToArtworkDetail(val artworkId: Int) : ArtistDetailNavigationEvent
}