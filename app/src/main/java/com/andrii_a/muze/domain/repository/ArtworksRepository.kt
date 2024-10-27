package com.andrii_a.muze.domain.repository

import androidx.paging.PagingData
import com.andrii_a.muze.domain.util.Resource
import com.andrii_a.muze.domain.models.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtworksRepository {

    fun getAllArtworks(): Flow<PagingData<Artwork>>

    fun getArtworksByArtist(artistId: Int): Flow<PagingData<Artwork>>

    fun getArtwork(id: Int): Flow<Resource<Artwork>>

}