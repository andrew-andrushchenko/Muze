package com.andrii_a.muze.ui.artworks

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R
import com.andrii_a.muze.ui.util.ArtworksLayoutType
import com.andrii_a.muze.ui.common.ArtworksGridContent
import com.andrii_a.muze.ui.common.ArtworksListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworksScreen(
    state: ArtworksUiState,
    onEvent: (ArtworksEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.artworks)) },
                actions = {
                    IconButton(
                        onClick = {
                            val newLayoutType = when (state.artworksLayoutType) {
                                ArtworksLayoutType.DEFAULT -> ArtworksLayoutType.STAGGERED_GRID
                                ArtworksLayoutType.STAGGERED_GRID -> ArtworksLayoutType.DEFAULT
                            }

                            onEvent(ArtworksEvent.ChangeLayoutType(newLayoutType))
                        }
                    ) {
                        val icon = when (state.artworksLayoutType) {
                            ArtworksLayoutType.DEFAULT -> Icons.Outlined.ViewCompact
                            ArtworksLayoutType.STAGGERED_GRID -> Icons.AutoMirrored.Outlined.ViewList
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = null
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
                        onArtworkSelected = { onEvent(ArtworksEvent.SelectArtwork(it)) },
                        addNavigationBarPadding = true,
                        contentPadding = innerPadding
                    )
                }

                ArtworksLayoutType.STAGGERED_GRID -> {
                    ArtworksGridContent(
                        artworkItems = lazyArtworkItems,
                        onArtworkSelected = { onEvent(ArtworksEvent.SelectArtwork(it)) },
                        addNavigationBarPadding = true,
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}