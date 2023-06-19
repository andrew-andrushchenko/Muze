package com.andrii_a.muze.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.andrii_a.muze.R
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.artworks.ArtworkItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    query: StateFlow<String>,
    artists: Flow<PagingData<Artist>>,
    artworks: Flow<PagingData<Artwork>>,
    onQueryChanged: (String) -> Unit,
    navigateToArtistDetail: (artistId: Int) -> Unit,
    navigateToArtworkDetail: (artworkId: Int) -> Unit
) {
    val pageState = rememberPagerState(initialPage = 0) { SearchScreenTabs.values().size }

    Box(modifier = Modifier.fillMaxSize()) {
        // Talkback focus order sorts based on x and y position before considering z-index. The
        // extra Box with semantics and fillMaxWidth is a workaround to get the search bar to focus
        // before the content.
        Box(
            modifier = Modifier
                .semantics {
                    @Suppress("DEPRECATION")
                    isContainer = true
                }
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            var text by remember { mutableStateOf(query.value) }
            var active by rememberSaveable { mutableStateOf(false) }

            Column {
                SearchBar(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = {
                        active = false
                        onQueryChanged(text)
                    },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = stringResource(id = R.string.type_artist_or_artwork_name)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                ) {

                }

                SearchTabs(pagerState = pageState)
            }
        }

        SearchPages(
            pagerState = pageState,
            artists = artists,
            artworks = artworks,
            navigateToArtistDetail = navigateToArtistDetail,
            navigateToArtworkDetail = navigateToArtworkDetail,
            modifier = Modifier.padding(top = 160.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchTabs(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(4.dp)
                    .padding(horizontal = 32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    ) {
        SearchScreenTabs.values().forEachIndexed { index, tabPage ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = tabPage.titleRes),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchPages(
    pagerState: PagerState,
    artists: Flow<PagingData<Artist>>,
    artworks: Flow<PagingData<Artwork>>,
    navigateToArtistDetail: (Int) -> Unit,
    navigateToArtworkDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val artistsItems = artists.collectAsLazyPagingItems()
    val artworksItems = artworks.collectAsLazyPagingItems()

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { pageIndex ->
        when (pageIndex) {
            SearchScreenTabs.Artists.ordinal -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = WindowInsets.systemBars.asPaddingValues()
                            .calculateBottomPadding() + 80.dp,
                    )
                ) {
                    items(
                        count = artistsItems.itemCount,
                        key = artistsItems.itemKey { it.id }
                    ) { index ->
                        val artist = artistsItems[index]
                        artist?.let {
                            ArtistItem(
                                artist = artist,
                                onClick = { navigateToArtistDetail(artist.id) },
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                        }
                    }
                }
            }

            SearchScreenTabs.Artworks.ordinal -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = WindowInsets.systemBars.asPaddingValues()
                            .calculateBottomPadding() + 80.dp,
                    )
                ) {
                    items(
                        count = artworksItems.itemCount,
                        key = artworksItems.itemKey { it.id }
                    ) { index ->
                        val artwork = artworksItems[index]
                        artwork?.let {
                            ArtworkItem(
                                artwork = artwork,
                                onArtworkClick = { navigateToArtworkDetail(artwork.id) },
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                        }
                    }
                }
            }

            else -> throw IllegalStateException("Tab screen was not declared!")
        }
    }
}

private enum class SearchScreenTabs(@StringRes val titleRes: Int) {
    Artists(R.string.artists),
    Artworks(R.string.artworks),
}
