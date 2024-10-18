package com.andrii_a.muze.ui.artists

sealed interface ArtistsNavigationEvent {
    data class NavigateToArtistDetail(val artistId: Int) : ArtistsNavigationEvent
}