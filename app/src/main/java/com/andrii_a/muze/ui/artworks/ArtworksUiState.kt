package com.andrii_a.muze.ui.artworks

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.andrii_a.muze.core.ArtworksLayoutType
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.util.emptyPagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
data class ArtworksUiState(
    private val artworksPagingData: PagingData<Artwork> = emptyPagingData(),
    val artworksLayoutType: ArtworksLayoutType = ArtworksLayoutType.DEFAULT
) {
    private val _artworks: MutableStateFlow<PagingData<Artwork>> = MutableStateFlow(emptyPagingData())
    val artworks: StateFlow<PagingData<Artwork>> = _artworks.asStateFlow()

    init {
        _artworks.update { artworksPagingData }
    }
}
