package com.andrii_a.muze.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andrii_a.muze.data.service.ArtistsService
import com.andrii_a.muze.data.source.ArtistsPagingSource
import com.andrii_a.muze.data.util.PAGE_SIZE
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.repository.ArtistsRepository
import com.andrii_a.muze.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArtistsRepositoryImpl(private val artistsService: ArtistsService) : ArtistsRepository {
    override fun getAllArtists(): Flow<PagingData<Artist>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArtistsPagingSource(artistsService) }
        ).flow

    override fun getArtist(id: Int): Flow<Resource<Artist>> = flow {
        emit(Resource.Loading)

        when (val result = artistsService.getArtist(id)) {
            is Resource.Error -> emit(result)
            is Resource.Success -> emit(Resource.Success(result.value.toArtist()))
            else -> Unit
        }
    }
}