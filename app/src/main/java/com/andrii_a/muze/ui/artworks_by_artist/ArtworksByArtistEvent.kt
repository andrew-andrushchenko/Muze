package com.andrii_a.muze.ui.artworks_by_artist

import com.andrii_a.muze.core.ArtworksLayoutType

sealed interface ArtworksByArtistEvent {
    data class RequestArtworks(val artistId: Int) : ArtworksByArtistEvent

    data class SelectArtwork(val artworkId: Int) : ArtworksByArtistEvent

    data class ChangeLayoutType(val layoutType: ArtworksLayoutType) : ArtworksByArtistEvent

    data object GoBack : ArtworksByArtistEvent
}