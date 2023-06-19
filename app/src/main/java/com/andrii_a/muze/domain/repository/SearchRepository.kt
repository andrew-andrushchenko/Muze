package com.andrii_a.muze.domain.repository

import androidx.paging.PagingData
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchArtists(query: String): Flow<PagingData<Artist>>

    fun searchArtworks(query: String): Flow<PagingData<Artwork>>

}