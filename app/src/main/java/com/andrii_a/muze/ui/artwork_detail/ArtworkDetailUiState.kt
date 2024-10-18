package com.andrii_a.muze.ui.artwork_detail

import androidx.compose.runtime.Stable
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.common.UiError

@Stable
data class ArtworkDetailUiState(
    val artwork: Artwork? = null,
    val error: UiError? = null,
    val isLoading: Boolean = false,
    val isInfoDialogOpened: Boolean = false,
)
