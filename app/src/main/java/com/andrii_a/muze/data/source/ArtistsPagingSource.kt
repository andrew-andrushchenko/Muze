package com.andrii_a.muze.data.source

import com.andrii_a.muze.data.base.BasePagingSource
import com.andrii_a.muze.data.service.ArtistsService
import com.andrii_a.muze.data.util.INITIAL_PAGE_INDEX
import com.andrii_a.muze.data.util.PAGE_SIZE
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.util.Resource

class ArtistsPagingSource(private val artistsService: ArtistsService) : BasePagingSource<Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val pageKey = params.key ?: INITIAL_PAGE_INDEX

        return try {
            val result = artistsService.getArtists(
                page = pageKey,
                perPage = PAGE_SIZE,
            )

            val artists: List<Artist> = when (result) {
                is Resource.Empty, Resource.Loading -> emptyList()
                is Resource.Error -> throw result.asException()
                is Resource.Success -> result.value.map { it.toArtist() }
            }

            LoadResult.Page(
                data = artists,
                prevKey = if (pageKey == INITIAL_PAGE_INDEX) null else pageKey - 1,
                nextKey = if (artists.isEmpty()) null else pageKey + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}