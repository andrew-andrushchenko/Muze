package com.andrii_a.muze.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.repository.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(artistsRepository: ArtistsRepository) : ViewModel() {

    val artists: Flow<PagingData<Artist>> =
        artistsRepository.getAllArtists().cachedIn(viewModelScope)

}