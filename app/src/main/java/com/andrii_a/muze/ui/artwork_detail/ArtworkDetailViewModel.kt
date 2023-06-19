package com.andrii_a.muze.ui.artwork_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrii_a.muze.core.BackendResult
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.domain.repository.ArtworksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface ArtworkLoadResult {
    object Empty : ArtworkLoadResult
    object Loading : ArtworkLoadResult
    object Error : ArtworkLoadResult
    data class Success(val artwork: Artwork) : ArtworkLoadResult
}

@HiltViewModel
class ArtworkDetailViewModel @Inject constructor(
    private val artworksRepository: ArtworksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _loadResult: MutableStateFlow<ArtworkLoadResult> = MutableStateFlow(ArtworkLoadResult.Empty)
    val loadResult: StateFlow<ArtworkLoadResult> = _loadResult.asStateFlow()

    init {
        savedStateHandle.get<Int>("artworkId")?.let { artistId ->
            getArtwork(artistId)
        }
    }

    private fun getArtwork(artworkId: Int) {
        artworksRepository.getArtwork(artworkId).onEach { result ->
            when (result) {
                is BackendResult.Empty -> Unit
                is BackendResult.Error -> _loadResult.update { ArtworkLoadResult.Error }
                is BackendResult.Loading -> _loadResult.update { ArtworkLoadResult.Loading }
                is BackendResult.Success -> _loadResult.update { ArtworkLoadResult.Success(result.value) }
            }
        }.launchIn(viewModelScope)
    }

}