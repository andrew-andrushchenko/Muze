package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtworkDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtworksService {
    @GET("artworks")
    suspend fun getArtworks(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): List<ArtworkDto>

    @GET("artworks/by-artist/{id}")
    suspend fun getArtworksByArtist(
        @Path("id") artistId: Int,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): List<ArtworkDto>

    @GET("artworks/{id}")
    suspend fun getArtwork(@Path("id") id: Int): ArtworkDto


}