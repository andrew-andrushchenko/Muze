package com.andrii_a.muze.ui.artworks_by_artist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.repository.ArtworksRepository
import com.andrii_a.muze.ui.navigation.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtworksByArtistViewModel(
    private val artworksRepository: ArtworksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<ArtworksByArtistUiState> = MutableStateFlow(ArtworksByArtistUiState())
    val state: StateFlow<ArtworksByArtistUiState> = _state.asStateFlow()

    init {
        val artistId = savedStateHandle.toRoute<Screen.ArtworksByArtist>().artistId
        onEvent(ArtworksByArtistEvent.RequestArtworks(artistId))
    }

    private val navigationChannel = Channel<ArtworksByArtistNavigationEvent>()
    val navigationEventsFlow = navigationChannel.receiveAsFlow()

    fun onEvent(event: ArtworksByArtistEvent) {
        when (event) {
            is ArtworksByArtistEvent.RequestArtworks -> {
                viewModelScope.launch {
                    artworksRepository.getArtworksByArtist(event.artistId).cachedIn(viewModelScope)
                        .collect { pagingData ->
                            _state.update { it.copy(artworksPagingData = pagingData) }
                        }
                }
            }

            is ArtworksByArtistEvent.SelectArtwork -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtworksByArtistNavigationEvent.NavigateToArtworkDetail(event.artworkId))
                }
            }

            is ArtworksByArtistEvent.ChangeLayoutType -> {
                _state.update { it.copy(artworksLayoutType = event.layoutType) }
            }

            is ArtworksByArtistEvent.GoBack -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtworksByArtistNavigationEvent.NavigateBack)
                }
            }
        }
    }
}