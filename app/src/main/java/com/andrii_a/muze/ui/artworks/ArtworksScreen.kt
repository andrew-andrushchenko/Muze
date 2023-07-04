package com.andrii_a.muze.ui.artworks

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ViewCompact
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R
import com.andrii_a.muze.core.ArtworksLayoutType
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.common.ArtworksGridContent
import com.andrii_a.muze.ui.common.ArtworksListContent
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworksScreen(
    artworksFlow: Flow<PagingData<Artwork>>,
    onArtworkSelected: (artworkId: Int) -> Unit
) {
    var layoutType by rememberSaveable {
        mutableStateOf(ArtworksLayoutType.DEFAULT)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.artworks)) },
                actions = {
                    IconButton(
                        onClick = {
                            layoutType = when (layoutType) {
                                ArtworksLayoutType.DEFAULT -> ArtworksLayoutType.STAGGERED_GRID
                                ArtworksLayoutType.STAGGERED_GRID -> ArtworksLayoutType.DEFAULT
                            }
                        }
                    ) {
                        val icon = when (layoutType) {
                            ArtworksLayoutType.DEFAULT -> Icons.Outlined.ViewCompact
                            ArtworksLayoutType.STAGGERED_GRID -> Icons.Outlined.ViewList
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
        val artworks = artworksFlow.collectAsLazyPagingItems()

        AnimatedContent(targetState = layoutType, label = "") { layout ->
            when (layout) {
                ArtworksLayoutType.DEFAULT -> {
                    ArtworksListContent(
                        artworks = artworks,
                        onArtworkSelected = onArtworkSelected,
                        contentPadding = innerPadding
                    )
                }

                ArtworksLayoutType.STAGGERED_GRID -> {
                    ArtworksGridContent(
                        artworks = artworks,
                        onArtworkSelected = onArtworkSelected,
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}