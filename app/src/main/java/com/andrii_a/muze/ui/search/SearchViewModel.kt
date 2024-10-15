package com.andrii_a.muze.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    //savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun onQueryChanged(query: String) {
        _query.update { query }
    }

    val artists: Flow<PagingData<Artist>> = _query.flatMapLatest { query ->
        searchRepository.searchArtists(query).cachedIn(viewModelScope)
    }

    val artworks: Flow<PagingData<Artwork>> = _query.flatMapLatest { query ->
        searchRepository.searchArtworks(query).cachedIn(viewModelScope)
    }

}