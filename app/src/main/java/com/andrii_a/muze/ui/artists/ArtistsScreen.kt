package com.andrii_a.muze.ui.artists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    state: ArtistsUiState,
    onEvent: (ArtistsEvent) -> Unit
) {
    val lazyArtistItems by rememberUpdatedState(newValue = state.artists.collectAsLazyPagingItems())
    val pagerState = rememberPagerState(initialPage = 0) { lazyArtistItems.itemCount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.artists)) },
                actions = {
                    CircleIndicator(
                        state = pagerState,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 80.dp)
        ) {
            ArtistsPager(
                artistItems = lazyArtistItems,
                pagerState = pagerState,
                navigateToArtistDetail = { onEvent(ArtistsEvent.SelectArtist(it)) }
            )
        }
    }
}
