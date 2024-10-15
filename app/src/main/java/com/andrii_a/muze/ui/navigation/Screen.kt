package com.andrii_a.muze.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data class ArtistDetail(val artistId: Int) : Screen

    @Serializable
    data class ArtworkDetail(val artworkId: Int) : Screen

    @Serializable
    data class ArtworksByArtist(val artistId: Int, val artistName: String) : Screen

    @Serializable
    data object Artists : Screen

    @Serializable
    data object Artworks : Screen

    @Serializable
    data object Search : Screen
}