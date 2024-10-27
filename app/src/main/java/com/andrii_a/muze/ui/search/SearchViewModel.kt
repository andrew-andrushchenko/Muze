package com.andrii_a.muze.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.repository.SearchRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _state: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private val navigationChannel = Channel<SearchNavigationEvent>()
    val navigationEventsFlow = navigationChannel.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.PerformSearch -> {
                performSearch(event.query)
            }

            is SearchEvent.SelectArtist -> {
                viewModelScope.launch {
                    navigationChannel.send(SearchNavigationEvent.NavigateToArtistDetail(event.artistId))
                }
            }

            is SearchEvent.SelectArtwork -> {
                viewModelScope.launch {
                    navigationChannel.send(SearchNavigationEvent.NavigateToArtworkDetail(event.artworkId))
                }
            }
        }
    }

    private fun performSearch(query: String) {
        val artistsFlow = searchRepository.searchArtists(query).cachedIn(viewModelScope)
        val artworksFlow = searchRepository.searchArtworks(query).cachedIn(viewModelScope)

        combine(artistsFlow, artworksFlow) { artistsPagingData, artworksPagingData ->
            _state.update {
                it.copy(
                    query = query,
                    artistsPagingData = artistsPagingData,
                    artworksPagingData = artworksPagingData
                )
            }
        }.launchIn(viewModelScope)
    }

}