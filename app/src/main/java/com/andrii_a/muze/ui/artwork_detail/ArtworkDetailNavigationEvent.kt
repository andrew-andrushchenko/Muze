package com.andrii_a.muze.ui.artwork_detail

sealed interface ArtworkDetailNavigationEvent {
    data object NavigateBack : ArtworkDetailNavigationEvent
}