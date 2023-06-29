package com.andrii_a.muze.ui.artworks

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.andrii_a.muze.R
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.common.AspectRatioImage
import com.andrii_a.muze.ui.common.EmptyContentBanner
import com.andrii_a.muze.ui.common.ErrorBanner
import com.andrii_a.muze.ui.common.ErrorItem
import com.andrii_a.muze.ui.common.LoadingListItem

@Composable
fun ArtworksColumn(
    lazyArtworkItems: LazyPagingItems<Artwork>,
    onArtworkClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        when (lazyArtworkItems.loadState.refresh) {
            is LoadState.NotLoading -> {
                if (lazyArtworkItems.itemCount > 0) {
                    items(
                        count = lazyArtworkItems.itemCount,
                        key = lazyArtworkItems.itemKey { it.id }
                    ) { index ->
                        val artwork = lazyArtworkItems[index]
                        artwork?.let {
                            DefaultArtworkItem(
                                artwork = artwork,
                                onArtworkClick = { onArtworkClick(artwork.id) },
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                        }
                    }
                } else {
                    item {
                        EmptyContentBanner(modifier = Modifier.fillParentMaxSize())
                    }
                }
            }

            is LoadState.Loading -> Unit

            is LoadState.Error -> {
                item {
                    ErrorBanner(
                        onRetry = lazyArtworkItems::retry,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }

        when (lazyArtworkItems.loadState.append) {
            is LoadState.NotLoading -> Unit

            is LoadState.Loading -> {
                item {
                    LoadingListItem(modifier = Modifier.fillParentMaxWidth())
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorItem(
                        onRetry = lazyArtworkItems::retry,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtworksStaggeredGrid(
    lazyArtworkItems: LazyPagingItems<Artwork>,
    onArtworkClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        state = gridState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = contentPadding
    ) {
        when (lazyArtworkItems.loadState.refresh) {
            is LoadState.NotLoading -> {
                if (lazyArtworkItems.itemCount > 0) {
                    items(
                        count = lazyArtworkItems.itemCount,
                        key = lazyArtworkItems.itemKey { it.id }
                    ) { index ->
                        val artwork = lazyArtworkItems[index]
                        artwork?.let {
                            GridArtworkItem(
                                artwork = artwork,
                                onArtworkClick = { onArtworkClick(artwork.id) }
                            )
                        }
                    }
                } else {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        EmptyContentBanner(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            is LoadState.Loading -> Unit

            is LoadState.Error -> {
                item(span = StaggeredGridItemSpan.FullLine) {
                    ErrorBanner(
                        onRetry = lazyArtworkItems::retry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        when (lazyArtworkItems.loadState.append) {
            is LoadState.NotLoading -> Unit

            is LoadState.Loading -> {
                item(span = StaggeredGridItemSpan.FullLine) {
                    LoadingListItem(modifier = Modifier.fillMaxWidth())
                }
            }

            is LoadState.Error -> {
                item(span = StaggeredGridItemSpan.FullLine) {
                    ErrorItem(
                        onRetry = lazyArtworkItems::retry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultArtworkItem(
    artwork: Artwork,
    onArtworkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = onArtworkClick,
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (artworkImage, artworkNameText, descriptionText, artistName, yearText) = createRefs()

            AspectRatioImage(
                width = artwork.image.width.toFloat(),
                height = artwork.image.height.toFloat(),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artwork.image.url)
                        .crossfade(durationMillis = 1000)
                        .scale(Scale.FILL)
                        .placeholder(ColorDrawable(android.graphics.Color.GRAY))
                        .build()
                ),
                description = stringResource(id = R.string.artwork_item),
                clickable = false,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                modifier = Modifier
                    .constrainAs(artworkImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            if (!artwork.year.isNullOrEmpty()) {
                YearIndicatorText(
                    yearStr = artwork.year,
                    modifier = Modifier.constrainAs(yearText) {
                        top.linkTo(parent.top, 8.dp)
                        end.linkTo(parent.end, 8.dp)
                    }
                )
            }

            Text(
                text = artwork.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(artworkNameText) {
                    top.linkTo(artworkImage.bottom, 8.dp)
                    bottom.linkTo(artistName.top, 0.dp)
                    start.linkTo(parent.start, 12.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = stringResource(id = R.string.by_formatted, artwork.artist.name),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(artistName) {
                    start.linkTo(parent.start, 12.dp)
                    end.linkTo(parent.end, 12.dp)
                    if (!artwork.description.isNullOrBlank()) {
                        bottom.linkTo(descriptionText.top, 8.dp)
                    } else {
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
                    width = Dimension.fillToConstraints
                }
            )

            if (!artwork.description.isNullOrBlank()) {
                Text(
                    text = artwork.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(descriptionText) {
                        start.linkTo(parent.start, 12.dp)
                        end.linkTo(parent.end, 12.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        }
    }
}

@Composable
private fun ArtistRow(
    artist: Artist,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.65f)
                    )
                )
            )
            .padding(8.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artist.portraitImage.url)
                .crossfade(durationMillis = 1000)
                .placeholder(ColorDrawable(Color.Gray.toArgb()))
                .build(),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
        )

        Text(
            text = artist.name,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GridArtworkItem(
    artwork: Artwork,
    onArtworkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = onArtworkClick,
        modifier = modifier
    ) {
        Box {
            AspectRatioImage(
                width = artwork.image.width.toFloat(),
                height = artwork.image.height.toFloat(),
                description = stringResource(id = R.string.artwork_item),
                onClick = onArtworkClick,
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artwork.image.url)
                        .crossfade(durationMillis = 1000)
                        .scale(Scale.FILL)
                        .placeholder(ColorDrawable(android.graphics.Color.GRAY))
                        .build()
                )
            )

            ArtistRow(
                artist = artwork.artist,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
private fun YearIndicatorText(
    yearStr: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = yearStr,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onPrimary,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    )
}


