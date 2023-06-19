package com.andrii_a.muze.data.source

import com.andrii_a.muze.data.base.BasePagingSource
import com.andrii_a.muze.data.service.ArtistsService
import com.andrii_a.muze.data.util.INITIAL_PAGE_INDEX
import com.andrii_a.muze.data.util.PAGE_SIZE
import com.andrii_a.muze.domain.models.Artist
import retrofit2.HttpException
import java.io.IOException

class ArtistsPagingSource(private val artistsService: ArtistsService) : BasePagingSource<Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val pageKey = params.key ?: INITIAL_PAGE_INDEX

        return try {
            val artists: List<Artist> = artistsService.getArtists(
                page = pageKey,
                perPage = PAGE_SIZE,
            ).map { it.toArtist() }

            LoadResult.Page(
                data = artists,
                prevKey = if (pageKey == INITIAL_PAGE_INDEX) null else pageKey - 1,
                nextKey = if (artists.isEmpty()) null else pageKey + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}