package com.andrii_a.muze.domain.models

data class Artwork(
    val id: Int,
    val name: String,
    val year: String?,
    val location: String,
    val imageUrl: String,
    val description: String?,
    val artist: Artist
)
