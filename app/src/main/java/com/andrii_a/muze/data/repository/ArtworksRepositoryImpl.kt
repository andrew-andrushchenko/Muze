package com.andrii_a.muze.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andrii_a.muze.core.BackendResult
import com.andrii_a.muze.data.service.ArtworksService
import com.andrii_a.muze.data.source.ArtworksByArtistPagingSource
import com.andrii_a.muze.data.source.ArtworksPagingSource
import com.andrii_a.muze.data.util.PAGE_SIZE
import com.andrii_a.muze.data.util.backendRequestFlow
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.domain.repository.ArtworksRepository
import kotlinx.coroutines.flow.Flow

class ArtworksRepositoryImpl(private val artworksService: ArtworksService) : ArtworksRepository {

    override fun getAllArtworks(): Flow<PagingData<Artwork>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArtworksPagingSource(artworksService) }
        ).flow

    override fun getArtworksByArtist(artistId: Int): Flow<PagingData<Artwork>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArtworksByArtistPagingSource(artistId, artworksService) }
        ).flow

    override fun getArtwork(id: Int): Flow<BackendResult<Artwork>> = backendRequestFlow {
        artworksService.getArtwork(id).toArtwork()
    }
}