package com.andrii_a.muze.data.dto

import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork

data class ArtworkDto(
    val id: Int,
    val name: String,
    val year: String?,
    val location: String,
    val image: ImageDto,
    val description: String?,
    val artist: Artist
) {
    fun toArtwork(): Artwork = Artwork(id, name, year, location, image.toImage(), description, artist)
}
