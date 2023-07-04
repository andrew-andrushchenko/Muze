package com.andrii_a.muze.ui.artworks_by_artist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.repository.ArtworksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtworksByArtistViewModel @Inject constructor(
    private val artworksRepository: ArtworksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _artistId: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        savedStateHandle.get<Int>("artistId")?.let { id ->
            viewModelScope.launch {
                _artistId.update { id }
            }
        }
    }

    val artworks = _artistId.flatMapLatest { id ->
        artworksRepository.getArtworksByArtist(id)
    }.cachedIn(viewModelScope)
}