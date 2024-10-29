package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import com.andrii_a.muze.domain.util.Resource

interface ArtistsService {

    suspend fun getArtists(
        page: Int?,
        perPage: Int?
    ): Resource<List<ArtistDto>>

    suspend fun getArtist(id: Int): Resource<ArtistDto>
}