package com.andrii_a.muze.ui.artist_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.util.Resource
import com.andrii_a.muze.domain.repository.ArtistsRepository
import com.andrii_a.muze.domain.repository.ArtworksRepository
import com.andrii_a.muze.ui.common.UiErrorWithRetry
import com.andrii_a.muze.ui.common.UiText
import com.andrii_a.muze.ui.navigation.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtistDetailViewModel(
    private val artistsRepository: ArtistsRepository,
    private val artworksRepository: ArtworksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<ArtistDetailUiState> = MutableStateFlow(ArtistDetailUiState())
    val state = _state.asStateFlow()

    private val navigationChannel = Channel<ArtistDetailNavigationEvent>()
    val navigationEventsFlow = navigationChannel.receiveAsFlow()

    init {
        val artistId = savedStateHandle.toRoute<Screen.ArtistDetail>().artistId
        onEvent(ArtistDetailEvent.RequestArtist(artistId))
    }

    fun onEvent(event: ArtistDetailEvent) {
        when(event) {
            is ArtistDetailEvent.RequestArtist -> {
                getArtist(event.artistId)
            }

            is ArtistDetailEvent.SelectArtwork -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtistDetailNavigationEvent.NavigateToArtworkDetail(event.artworkId))
                }
            }

            is ArtistDetailEvent.SelectMoreArtworks -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtistDetailNavigationEvent.NavigateToArtistArtworks(event.artistId))
                }
            }

            is ArtistDetailEvent.GoBack -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtistDetailNavigationEvent.NavigateBack)
                }
            }
        }
    }

    private fun getArtist(artistId: Int) {
        artistsRepository.getArtist(artistId).onEach { result ->
            when (result) {
                is Resource.Empty -> Unit

                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = UiErrorWithRetry(
                                reason = UiText.DynamicString(result.reason.orEmpty()),
                                onRetry = {
                                    onEvent(
                                        ArtistDetailEvent.RequestArtist(artistId)
                                    )
                                }
                            )
                        )
                    }
                }


                is Resource.Success -> {
                    val artist = result.value

                    viewModelScope.launch {
                        artworksRepository.getArtworksByArtist(artist.id).cachedIn(viewModelScope)
                            .collect { pagingData ->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = null,
                                        artist = artist,
                                        artistArtworksPagingData = pagingData
                                    )
                                }
                            }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}