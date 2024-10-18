package com.andrii_a.muze.ui.artist_detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.andrii_a.muze.R
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.domain.models.Image
import com.andrii_a.muze.ui.common.ErrorBanner
import com.andrii_a.muze.ui.common.UiErrorWithRetry
import com.andrii_a.muze.ui.theme.BottleCapShape
import com.andrii_a.muze.ui.theme.MuzeTheme
import com.andrii_a.muze.ui.util.lifeYearsString

@Composable
fun ArtistDetailScreen(
    state: ArtistDetailUiState,
    onEvent: (ArtistDetailEvent) -> Unit
) {
    when {
        state.isLoading -> {
            LoadingStateContent(
                onNavigateBack = { onEvent(ArtistDetailEvent.GoBack) }
            )
        }

        !state.isLoading && state.error == null && state.artist != null -> {
            SuccessStateContent(
                state = state,
                onEvent = onEvent
            )
        }

        else -> {
            val error = state.error as? UiErrorWithRetry
            Toast.makeText(LocalContext.current, error?.reason?.asString(), Toast.LENGTH_SHORT).show()

            ErrorStateContent(
                onRetry = {
                    error?.onRetry?.invoke()
                },
                onNavigateBack = { onEvent(ArtistDetailEvent.GoBack) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadingStateContent(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorStateContent(
    onRetry: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ErrorBanner(
            onRetry = onRetry,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessStateContent(
    state: ArtistDetailUiState,
    onEvent: (ArtistDetailEvent) -> Unit
) {
    val artist = state.artist!!

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = artist.name) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(ArtistDetailEvent.GoBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.portraitImage.url)
                    .crossfade(durationMillis = 1000)
                    .scale(Scale.FILL)
                    .placeholder(ColorDrawable(Color.GRAY))
                    .build(),
                contentDescription = stringResource(id = R.string.artist_portrait),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(BottleCapShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = artist.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = artist.lifeYearsString,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                var expanded by remember {
                    mutableStateOf(false)
                }

                Text(
                    text = artist.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (expanded) Int.MAX_VALUE else 12,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { expanded = !expanded }
                        .animateContentSize()
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.artworks_by_formatted, artist.name),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val artworks = state.artistArtworks.collectAsLazyPagingItems()
            val artworksSnapshotList = artworks.itemSnapshotList.take(8)

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(count = artworksSnapshotList.size) { index ->
                    val artwork = artworksSnapshotList[index]
                    artwork?.let {
                        SmallArtworkItem(
                            imageUrl = artwork.image.url,
                            onClick = { onEvent(ArtistDetailEvent.SelectArtwork(artwork.id)) },
                            modifier = Modifier.padding(
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                        )
                    }
                }

                item {
                    FloatingActionButton(
                        onClick = {
                            onEvent(ArtistDetailEvent.SelectMoreArtworks(artist.id))
                            //navigateToArtistArtworks(artist.id, artist.name)
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = stringResource(id = R.string.show_all_artworks_by_artist)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ArtistDetailScreenPreview() {
    MuzeTheme {
        Surface {
            val artist = Artist(
                id = 0,
                name = "Rene Magritte",
                bornDateString = "1890-01-12",
                diedDateString = "1956-04-10",
                portraitImage = Image(
                    width = 200,
                    height = 200,
                    url = ""
                ),
                bio = "lorem ipsum".repeat(5)
            )

            val artwork = Artwork(
                id = 0,
                name = "artwork",
                year = "1990",
                location = "London",
                image = Image(
                    width = 200,
                    height = 200,
                    url = ""
                ),
                description = "",
                artist = artist
            )

            val state = ArtistDetailUiState(
                artist = artist,
                isLoading = false,
                error = null,
                artistArtworksPagingData = PagingData.from(listOf(artwork, artwork))
            )

            ArtistDetailScreen(
                state = state,
                onEvent = {}
            )
        }
    }
}