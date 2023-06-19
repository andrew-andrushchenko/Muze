package com.andrii_a.muze.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andrii_a.muze.data.service.SearchService
import com.andrii_a.muze.data.source.SearchArtistsPagingSource
import com.andrii_a.muze.data.source.SearchArtworksPagingSource
import com.andrii_a.muze.data.util.PAGE_SIZE
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(private val searchService: SearchService) : SearchRepository {

    override fun searchArtists(query: String): Flow<PagingData<Artist>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchArtistsPagingSource(query, searchService) }
        ).flow

    override fun searchArtworks(query: String): Flow<PagingData<Artwork>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchArtworksPagingSource(query, searchService) }
        ).flow
}