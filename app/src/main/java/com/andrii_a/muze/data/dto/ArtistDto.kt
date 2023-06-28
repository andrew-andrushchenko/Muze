package com.andrii_a.muze.data.dto

import com.andrii_a.muze.domain.models.Artist

data class ArtistDto(
    val id: Int,
    val name: String,
    val bornDateString: String?,
    val diedDateString: String?,
    val portraitImage: ImageDto,
    val bio: String
) {
    fun toArtist(): Artist = Artist(id, name, bornDateString, diedDateString, portraitImage.toImage(), bio)
}