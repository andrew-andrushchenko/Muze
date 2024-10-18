package com.andrii_a.muze.ui.search

sealed interface SearchNavigationEvent {
    data class NavigateToArtistDetail(val artistId: Int) : SearchNavigationEvent

    data class NavigateToArtworkDetail(val artworkId: Int) : SearchNavigationEvent
}