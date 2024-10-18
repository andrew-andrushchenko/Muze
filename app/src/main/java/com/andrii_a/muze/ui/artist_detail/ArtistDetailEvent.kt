package com.andrii_a.muze.ui.artist_detail

sealed interface ArtistDetailEvent {
    data class RequestArtist(val artistId: Int) : ArtistDetailEvent

    data class SelectArtwork(val artworkId: Int) : ArtistDetailEvent

    data class SelectMoreArtworks(val artistId: Int) : ArtistDetailEvent

    data object GoBack : ArtistDetailEvent
}