package com.andrii_a.muze.ui.search

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.util.emptyPagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
data class SearchUiState(
    val query: String = "",
    private val artistsPagingData: PagingData<Artist> = emptyPagingData(),
    private val artworksPagingData: PagingData<Artwork> = emptyPagingData()
) {
    private val _artists: MutableStateFlow<PagingData<Artist>> = MutableStateFlow(emptyPagingData())
    val artists: StateFlow<PagingData<Artist>> = _artists.asStateFlow()

    private val _artworks: MutableStateFlow<PagingData<Artwork>> = MutableStateFlow(emptyPagingData())
    val artworks: StateFlow<PagingData<Artwork>> = _artworks.asStateFlow()

    init {
        _artists.update { artistsPagingData }
        _artworks.update { artworksPagingData }
    }
}
