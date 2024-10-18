package com.andrii_a.muze.ui.search

sealed interface SearchEvent {
    data class PerformSearch(val query: String) : SearchEvent

    data class SelectArtist(val artistId: Int) : SearchEvent

    data class SelectArtwork(val artworkId: Int) : SearchEvent
}