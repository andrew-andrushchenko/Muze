package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtworkDto
import com.andrii_a.muze.data.util.ARTWORKS_BY_ARTIST_URL
import com.andrii_a.muze.data.util.ARTWORKS_URL
import com.andrii_a.muze.data.util.backendRequest
import com.andrii_a.muze.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

class ArtworksServiceImpl(private val httpClient: HttpClient) : ArtworksService {

    override suspend fun getArtworks(page: Int?, perPage: Int?): Resource<List<ArtworkDto>> {
        return backendRequest {
            httpClient.get(urlString = ARTWORKS_URL) {
                parameter("page", page)
                parameter("per_page", perPage)
            }
        }
    }

    override suspend fun getArtworksByArtist(
        artistId: Int,
        page: Int?,
        perPage: Int?
    ): Resource<List<ArtworkDto>> {
        return backendRequest {
            httpClient.get(urlString = ARTWORKS_BY_ARTIST_URL) {
                url {
                    appendPathSegments(artistId.toString())
                    parameters.append("page", page.toString())
                    parameters.append("per_page", perPage.toString())
                }
            }
        }
    }

    override suspend fun getArtwork(id: Int): Resource<ArtworkDto> {
        return backendRequest {
            httpClient.get(urlString = ARTWORKS_URL) {
                url {
                    appendPathSegments(id.toString())
                }
            }
        }
    }
}