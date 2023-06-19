package com.andrii_a.muze.ui.artist_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.andrii_a.muze.core.BackendResult
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.repository.ArtistsRepository
import com.andrii_a.muze.domain.repository.ArtworksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ArtistLoadResult {
    object Empty : ArtistLoadResult
    object Loading : ArtistLoadResult
    object Error : ArtistLoadResult
    data class Success(val artist: Artist) : ArtistLoadResult
}

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val artistsRepository: ArtistsRepository,
    private val artworksRepository: ArtworksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _artistId: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _loadResult: MutableStateFlow<ArtistLoadResult> = MutableStateFlow(ArtistLoadResult.Empty)
    val loadResult: StateFlow<ArtistLoadResult> = _loadResult.asStateFlow()

    val artworks = _artistId.flatMapLatest { id ->
        artworksRepository.getArtworksByArtist(id).cachedIn(viewModelScope)
    }

    init {
        savedStateHandle.get<Int>("artistId")?.let { artistId ->
            getArtist(artistId)

            viewModelScope.launch {
                _artistId.emit(artistId)
            }
        }
    }

    private fun getArtist(artistId: Int) {
        artistsRepository.getArtist(artistId).onEach { result ->
            when (result) {
                is BackendResult.Empty -> Unit
                is BackendResult.Error -> _loadResult.update { ArtistLoadResult.Error }
                is BackendResult.Loading -> _loadResult.update { ArtistLoadResult.Loading }
                is BackendResult.Success -> _loadResult.update { ArtistLoadResult.Success(result.value) }
            }
        }.launchIn(viewModelScope)
    }

}