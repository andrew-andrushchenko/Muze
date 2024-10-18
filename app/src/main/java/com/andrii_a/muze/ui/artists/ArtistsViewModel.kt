package com.andrii_a.muze.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.repository.ArtistsRepository
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
class ArtistsViewModel @Inject constructor(private val artistsRepository: ArtistsRepository) : ViewModel() {

    private val _state: MutableStateFlow<ArtistsUiState> = MutableStateFlow(ArtistsUiState())
    val state: StateFlow<ArtistsUiState> = _state.asStateFlow()

    private val navigationChannel = Channel<ArtistsNavigationEvent>()
    val navigationEventsFlow = navigationChannel.receiveAsFlow()

    fun onEvent(event: ArtistsEvent) {
        when (event) {
            is ArtistsEvent.RequestArtists -> {
                viewModelScope.launch {
                    artistsRepository.getAllArtists().cachedIn(viewModelScope)
                        .collect { pagingData ->
                            _state.update {
                                it.copy(artistsPagingData = pagingData)
                            }
                        }
                }
            }

            is ArtistsEvent.SelectArtist -> {
                viewModelScope.launch {
                    navigationChannel.send(ArtistsNavigationEvent.NavigateToArtistDetail(event.artistId))
                }
            }
        }
    }

}