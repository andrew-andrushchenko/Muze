package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import com.andrii_a.muze.data.util.ARTISTS_URL
import com.andrii_a.muze.data.util.backendRequest
import com.andrii_a.muze.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

class ArtistsServiceImpl(private val httpClient: HttpClient) : ArtistsService {

    override suspend fun getArtists(page: Int?, perPage: Int?): Resource<List<ArtistDto>> {
        return backendRequest {
            httpClient.get(urlString = ARTISTS_URL) {
                parameter("page", page)
                parameter("per_page", perPage)
            }
        }
    }

    override suspend fun getArtist(id: Int): Resource<ArtistDto> {
        return backendRequest {
            httpClient.get(urlString = ARTISTS_URL) {
                url {
                    appendPathSegments(id.toString())
                }
            }
        }
    }
}