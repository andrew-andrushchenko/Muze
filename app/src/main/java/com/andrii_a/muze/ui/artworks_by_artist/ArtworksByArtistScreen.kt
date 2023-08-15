package com.andrii_a.muze.ui.artworks_by_artist

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ViewCompact
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
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
fun ArtworksByArtistScreen(
    artistName: String,
    artworksFlow: Flow<PagingData<Artwork>>,
    onArtworkSelected: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    var layoutType by rememberSaveable {
        mutableStateOf(ArtworksLayoutType.DEFAULT)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.artworks_by_formatted,
                            artistName
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back)
                        )
                    }
                },
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
                            contentDescription = stringResource(id = R.string.change_layout_type)
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
                        artworkItems = artworks,
                        onArtworkSelected = onArtworkSelected,
                        contentPadding = innerPadding
                    )
                }

                ArtworksLayoutType.STAGGERED_GRID -> {
                    ArtworksGridContent(
                        artworkItems = artworks,
                        onArtworkSelected = onArtworkSelected,
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}