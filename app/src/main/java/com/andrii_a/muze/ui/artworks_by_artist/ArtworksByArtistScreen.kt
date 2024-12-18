package com.andrii_a.muze.ui.artworks_by_artist

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.outlined.ViewCompact
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R
import com.andrii_a.muze.ui.util.ArtworksLayoutType
import com.andrii_a.muze.ui.common.ArtworksGridContent
import com.andrii_a.muze.ui.common.ArtworksListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworksByArtistScreen(
    state: ArtworksByArtistUiState,
    onEvent: (ArtworksByArtistEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.artworks_by_formatted,
                            "placeholder"
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(ArtworksByArtistEvent.GoBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val newLayoutType = when (state.artworksLayoutType) {
                                ArtworksLayoutType.DEFAULT -> ArtworksLayoutType.STAGGERED_GRID
                                ArtworksLayoutType.STAGGERED_GRID -> ArtworksLayoutType.DEFAULT
                            }

                            onEvent(ArtworksByArtistEvent.ChangeLayoutType(newLayoutType))
                        }
                    ) {
                        val icon = when (state.artworksLayoutType) {
                            ArtworksLayoutType.DEFAULT -> Icons.Outlined.ViewCompact
                            ArtworksLayoutType.STAGGERED_GRID -> Icons.AutoMirrored.Outlined.ViewList
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(id = R.string.change_layout_type)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        val lazyArtworkItems by rememberUpdatedState(newValue = state.artworks.collectAsLazyPagingItems())

        AnimatedContent(targetState = state.artworksLayoutType, label = "") { layout ->
            when (layout) {
                ArtworksLayoutType.DEFAULT -> {
                    ArtworksListContent(
                        artworkItems = lazyArtworkItems,
                        onArtworkSelected = { onEvent(ArtworksByArtistEvent.SelectArtwork(it)) },
                        contentPadding = innerPadding
                    )
                }

                ArtworksLayoutType.STAGGERED_GRID -> {
                    ArtworksGridContent(
                        artworkItems = lazyArtworkItems,
                        onArtworkSelected = { onEvent(ArtworksByArtistEvent.SelectArtwork(it)) },
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}