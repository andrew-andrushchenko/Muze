package com.andrii_a.muze.data.source

import com.andrii_a.muze.data.base.BasePagingSource
import com.andrii_a.muze.data.service.ArtworksService
import com.andrii_a.muze.data.service.SearchService
import com.andrii_a.muze.data.util.INITIAL_PAGE_INDEX
import com.andrii_a.muze.data.util.PAGE_SIZE
import com.andrii_a.muze.domain.models.Artwork
import retrofit2.HttpException
import java.io.IOException

class SearchArtworksPagingSource(
    private val query: String,
    private val searchService: SearchService
) : BasePagingSource<Artwork>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artwork> {
        val pageKey = params.key ?: INITIAL_PAGE_INDEX

        return try {
            if (query.isEmpty()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val photos: List<Artwork> = searchService.searchArtworks(
                query = query,
                page = pageKey,
                perPage = PAGE_SIZE,
            ).map { it.toArtwork() }

            LoadResult.Page(
                data = photos,
                prevKey = if (pageKey == INITIAL_PAGE_INDEX) null else pageKey - 1,
                nextKey = if (photos.isEmpty()) null else pageKey + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}