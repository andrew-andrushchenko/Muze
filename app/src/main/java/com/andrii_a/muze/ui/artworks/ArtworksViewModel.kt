package com.andrii_a.muze.ui.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.domain.repository.ArtworksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArtworksViewModel @Inject constructor(
    private val artworksRepository: ArtworksRepository
) : ViewModel() {

    val artworks: Flow<PagingData<Artwork>> =
        artworksRepository.getAllArtworks().cachedIn(viewModelScope)

}