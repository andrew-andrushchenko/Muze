package com.andrii_a.muze.ui.artworks

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ViewCompact
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R
import com.andrii_a.muze.core.ArtworksLayoutType
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.common.ScrollToTopLayout
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtworksListContent(
    artworks: LazyPagingItems<Artwork>,
    contentPadding: PaddingValues = PaddingValues(),
    onArtworkSelected: (artworkId: Int) -> Unit
) {
    val listState = rememberLazyListState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = artworks.loadState.refresh is LoadState.Loading,
        onRefresh = artworks::refresh,
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        ScrollToTopLayout(
            listState = listState,
            contentPadding = PaddingValues(
                bottom = WindowInsets.navigationBars.asPaddingValues()
                    .calculateBottomPadding() + 90.dp
            )
        ) {
            ArtworksList(
                lazyArtworkItems = artworks,
                onArtworkClick = onArtworkSelected,
                listState = listState,
                contentPadding = PaddingValues(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding() + 150.dp,
                    start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
                ),
                modifier = Modifier.fillMaxSize()
            )
        }

        PullRefreshIndicator(
            refreshing = artworks.loadState.refresh is LoadState.Loading,
            state = pullRefreshState,
            backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtworksGridContent(
    artworks: LazyPagingItems<Artwork>,
    contentPadding: PaddingValues = PaddingValues(),
    onArtworkSelected: (artworkId: Int) -> Unit
) {
    val gridState = rememberLazyStaggeredGridState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = artworks.loadState.refresh is LoadState.Loading,
        onRefresh = artworks::refresh,
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        ScrollToTopLayout(
            gridState = gridState,
            contentPadding = PaddingValues(
                bottom = WindowInsets.navigationBars.asPaddingValues()
                    .calculateBottomPadding() + 90.dp
            )
        ) {
            ArtworksStaggeredGrid(
                lazyArtworkItems = artworks,
                onArtworkClick = onArtworkSelected,
                gridState = gridState,
                contentPadding = PaddingValues(
                    top = contentPadding.calculateTopPadding() + 16.dp,
                    bottom = contentPadding.calculateBottomPadding() + 150.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                modifier = Modifier.fillMaxSize()
            )
        }

        PullRefreshIndicator(
            refreshing = artworks.loadState.refresh is LoadState.Loading,
            state = pullRefreshState,
            backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}