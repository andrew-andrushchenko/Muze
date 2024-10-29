package com.andrii_a.muze.data.service

import com.andrii_a.muze.data.dto.ArtistDto
import com.andrii_a.muze.data.util.ARTISTS_URL
import com.andrii_a.muze.data.util.asResource
import com.andrii_a.muze.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class ArtistsServiceImpl(private val httpClient: HttpClient) : ArtistsService {

    override suspend fun getArtists(page: Int?, perPage: Int?): Resource<List<ArtistDto>> {
        val response = try {
            httpClient.get(urlString = ARTISTS_URL) {
                parameter("page", page)
                parameter("per_page", perPage)
            }
        } catch (e: UnresolvedAddressException) {
            return Resource.Error(exception = e)
        } catch (e: SerializationException) {
            return Resource.Error(exception = e)
        }

        return response.asResource()
    }

    override suspend fun getArtist(id: Int): Resource<ArtistDto> {
        val response = try {
            httpClient.get(urlString = ARTISTS_URL) {
                url {
                    appendPathSegments(id.toString())
                }
            }
        } catch (e: UnresolvedAddressException) {
            return Resource.Error(exception = e)
        } catch (e: SerializationException) {
            return Resource.Error(exception = e)
        }

        return response.asResource()
    }
}