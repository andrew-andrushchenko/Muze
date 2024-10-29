package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import com.andrii_a.muze.data.dto.ArtworkDto
import com.andrii_a.muze.domain.util.Resource

interface SearchService {

    suspend fun searchArtists(
        query: String,
        page: Int?,
        perPage: Int?
    ): Resource<List<ArtistDto>>

    suspend fun searchArtworks(
        query: String,
        page: Int?,
        perPage: Int?
    ): Resource<List<ArtworkDto>>
}