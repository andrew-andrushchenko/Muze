package com.andrii_a.muze.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R
import com.andrii_a.muze.ui.artworks.ArtworksColumn
import com.andrii_a.muze.ui.common.ScrollToTopLayout
import com.andrii_a.muze.ui.theme.MuzeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUiState,
    onEvent: (SearchEvent) -> Unit
) {
    val pageState = rememberPagerState(initialPage = 0) { SearchScreenTabs.entries.size }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        var text by remember { mutableStateOf(state.query) }
        var active by rememberSaveable { mutableStateOf(false) }

        Column {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = {
                            active = false
                            onEvent(SearchEvent.PerformSearch(query = text))
                        },
                        expanded = active,
                        onExpandedChange = { active = it },
                        placeholder = { Text(text = stringResource(id = R.string.type_artist_or_artwork_name)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search_icon)) },
                    )
                },
                expanded = active,
                onExpandedChange = { active = it },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .semantics { traversalIndex = -1f },
            ) {

            }

            SearchTabs(pagerState = pageState)

            SearchPages(
                pagerState = pageState,
                uiState = state,
                onEvent = onEvent
            )
        }
    }
}

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
        SearchScreenTabs.entries.forEachIndexed { index, tabPage ->
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

@Composable
private fun SearchPages(
    pagerState: PagerState,
    uiState: SearchUiState,
    onEvent: (SearchEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyArtistItems by rememberUpdatedState(newValue = uiState.artists.collectAsLazyPagingItems())
    val lazyArtworkItems by rememberUpdatedState(newValue = uiState.artworks.collectAsLazyPagingItems())

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { pageIndex ->
        when (pageIndex) {
            SearchScreenTabs.Artists.ordinal -> {
                val listState = rememberLazyListState()

                ScrollToTopLayout(
                    listState = listState,
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding() + 90.dp
                    )
                ) {
                    ArtistsList(
                        artistItems = lazyArtistItems,
                        onArtistClick = { onEvent(SearchEvent.SelectArtist(it)) },
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = WindowInsets.systemBars.asPaddingValues()
                                .calculateBottomPadding() + 150.dp,
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            SearchScreenTabs.Artworks.ordinal -> {
                val listState = rememberLazyListState()

                ScrollToTopLayout(
                    listState = listState,
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding() + 90.dp
                    )
                ) {
                    ArtworksColumn(
                        lazyArtworkItems = lazyArtworkItems,
                        onArtworkClick = { onEvent(SearchEvent.SelectArtwork(it)) },
                        listState = listState,
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = WindowInsets.systemBars.asPaddingValues()
                                .calculateBottomPadding() + 150.dp,
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
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

@Preview
@Composable
private fun SearchScreenPreview() {
    MuzeTheme {
        Surface {
            val state = SearchUiState()

            SearchScreen(
                state = state,
                onEvent = {}
            )
        }
    }
}
