package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistsService {
    @GET("artists")
    suspend fun getArtists(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): List<ArtistDto>

    @GET("artists/{id}")
    suspend fun getArtist(@Path("id") id: Int): ArtistDto

}