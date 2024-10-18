package com.andrii_a.muze.ui.artwork_detail

sealed interface ArtworkDetailEvent {
    data class RequestArtwork(val artworkId: Int) : ArtworkDetailEvent

    data object ShowInfoDialog : ArtworkDetailEvent

    data object DismissInfoDialog : ArtworkDetailEvent

    data object GoBack : ArtworkDetailEvent
}