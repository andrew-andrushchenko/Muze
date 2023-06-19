package com.andrii_a.muze.domain.repository

import androidx.paging.PagingData
import com.andrii_a.muze.core.BackendResult
import com.andrii_a.muze.domain.models.Artist
import kotlinx.coroutines.flow.Flow

interface ArtistsRepository {

    fun getAllArtists(): Flow<PagingData<Artist>>

    fun getArtist(id: Int): Flow<BackendResult<Artist>>

}