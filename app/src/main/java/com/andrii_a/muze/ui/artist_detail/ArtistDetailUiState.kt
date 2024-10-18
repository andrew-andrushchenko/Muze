package com.andrii_a.muze.ui.artist_detail

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.common.UiError
import com.andrii_a.muze.ui.util.emptyPagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
data class ArtistDetailUiState(
    val artist: Artist? = null,
    val error: UiError? = null,
    val isLoading: Boolean = false,
    private val artistArtworksPagingData: PagingData<Artwork> = emptyPagingData()
) {
    private val _artistArtworks: MutableStateFlow<PagingData<Artwork>> = MutableStateFlow(
        emptyPagingData()
    )
    val artistArtworks: StateFlow<PagingData<Artwork>> = _artistArtworks.asStateFlow()

    init {
        _artistArtworks.update { artistArtworksPagingData }
    }
}
