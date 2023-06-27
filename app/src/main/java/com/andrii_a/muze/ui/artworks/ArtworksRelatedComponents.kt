package com.andrii_a.muze.ui.artworks

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.andrii_a.muze.R
import com.andrii_a.muze.domain.models.Artwork
import com.andrii_a.muze.ui.common.EmptyContentBanner
import com.andrii_a.muze.ui.common.ErrorBanner
import com.andrii_a.muze.ui.common.ErrorItem
import com.andrii_a.muze.ui.common.LoadingListItem

@Composable
fun ArtworksList(
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
                            ArtworkItem(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkItem(
    artwork: Artwork,
    onArtworkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = onArtworkClick,
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (artworkImage, artworkNameText, descriptionText, artistName, yearText) = createRefs()

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artwork.imageUrl)
                    .crossfade(durationMillis = 1000)
                    .scale(Scale.FILL)
                    .placeholder(ColorDrawable(android.graphics.Color.GRAY))
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
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
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(artworkNameText) {
                    top.linkTo(artworkImage.bottom, 8.dp)
                    bottom.linkTo(artistName.top, 0.dp)
                    start.linkTo(parent.start, 12.dp)
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
fun YearIndicatorText(
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
            .clip(RoundedCornerShape(50))
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    )
}


