package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import com.andrii_a.muze.data.dto.ArtworkDto
import com.andrii_a.muze.data.util.ARTISTS_SEARCH_URL
import com.andrii_a.muze.data.util.ARTWORKS_SEARCH_URL
import com.andrii_a.muze.data.util.backendRequest
import com.andrii_a.muze.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchServiceImpl(private val httpClient: HttpClient) : SearchService {

    override suspend fun searchArtists(
        query: String,
        page: Int?,
        perPage: Int?
    ): Resource<List<ArtistDto>> {
        return backendRequest {
            httpClient.get(urlString = ARTISTS_SEARCH_URL) {
                parameter("query", query)
                parameter("page", page)
                parameter("per_page", perPage)
            }
        }
    }

    override suspend fun searchArtworks(
        query: String,
        page: Int?,
        perPage: Int?
    ): Resource<List<ArtworkDto>> {
        return backendRequest {
            httpClient.get(urlString = ARTWORKS_SEARCH_URL) {
                parameter("query", query)
                parameter("page", page)
                parameter("per_page", perPage)
            }
        }
    }
}