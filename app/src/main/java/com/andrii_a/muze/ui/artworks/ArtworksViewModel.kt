package com.andrii_a.muze.ui.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.repository.ArtworksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtworksViewModel @Inject constructor(
    private val artworksRepository: ArtworksRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ArtworksUiState> = MutableStateFlow(ArtworksUiState())
    val state: StateFlow<ArtworksUiState> = _state.asStateFlow()

    private val navigationChannel = Channel<ArtworksNavigationEvent>()
    val navigationEventsFlow = navigationChannel.receiveAsFlow()

    fun onEvent(event: ArtworksEvent) {
        when (event) {
            is ArtworksEvent.RequestArtworks -> {
                viewModelScope.launch {
                    artworksRepository.getAllArtworks().cachedIn(viewModelScope)
                        .collect { pagingData ->
                            _state.update {
                                it.copy(artworksPagingData = pagingData)
                            }
                        }
                }
            }

            is ArtworksEvent.SelectArtwork -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtworksNavigationEvent.NavigateToArtworkDetail(event.artworkId))
                }
            }

            is ArtworksEvent.ChangeLayoutType -> {
                _state.update {
                    it.copy(artworksLayoutType = event.layoutType)
                }
            }
        }
    }
}