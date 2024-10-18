package com.andrii_a.muze.ui.artists

sealed interface ArtistsEvent {
    data object RequestArtists : ArtistsEvent

    data class SelectArtist(val artistId: Int) : ArtistsEvent
}