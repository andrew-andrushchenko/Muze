package com.andrii_a.muze.ui.artworks

import com.andrii_a.muze.core.ArtworksLayoutType

sealed interface ArtworksEvent {
    data object RequestArtworks : ArtworksEvent

    data class SelectArtwork(val artworkId: Int) : ArtworksEvent

    data class ChangeLayoutType(val layoutType: ArtworksLayoutType) : ArtworksEvent
}