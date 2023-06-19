package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import com.andrii_a.muze.data.dto.ArtworkDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("artists/search")
    suspend fun searchArtists(
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): List<ArtistDto>

    @GET("artworks/search")
    suspend fun searchArtworks(
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): List<ArtworkDto>

}