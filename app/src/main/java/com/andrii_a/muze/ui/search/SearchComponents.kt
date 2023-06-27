package com.andrii_a.muze.ui.search

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.ui.common.EmptyContentBanner
import com.andrii_a.muze.ui.common.ErrorBanner
import com.andrii_a.muze.ui.common.ErrorItem
import com.andrii_a.muze.ui.common.LoadingListItem
import com.andrii_a.muze.ui.util.lifeYearsString

@Composable
fun ArtistsList(
    lazyArtistItems: LazyPagingItems<Artist>,
    onArtistClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        when (lazyArtistItems.loadState.refresh) {
            is LoadState.NotLoading -> {
                if (lazyArtistItems.itemCount > 0) {
                    items(
                        count = lazyArtistItems.itemCount,
                        key = lazyArtistItems.itemKey { it.id }
                    ) { index ->
                        val artist = lazyArtistItems[index]
                        artist?.let {
                            ArtistItem(
                                artist = artist,
                                onClick = onArtistClick,
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
                        onRetry = lazyArtistItems::retry,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }

        when (lazyArtistItems.loadState.append) {
            is LoadState.NotLoading -> Unit

            is LoadState.Loading -> {
                item {
                    LoadingListItem(modifier = Modifier.fillParentMaxWidth())
                }
            }

            is LoadState.Error -> {
                item {
                    ErrorItem(
                        onRetry = lazyArtistItems::retry,
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
fun ArtistItem(
    artist: Artist,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(artist.id) },
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (nameText, portraitImage, lifeYearsText) = createRefs()

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artist.portraitImageUrl)
                    .crossfade(durationMillis = 1000)
                    .scale(Scale.FILL)
                    .placeholder(ColorDrawable(Color.Gray.toArgb()))
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(portraitImage) {
                        top.linkTo(parent.top, margin = 12.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                        end.linkTo(nameText.start, margin = 12.dp)
                        bottom.linkTo(parent.bottom, margin = 12.dp)
                    }
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = artist.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(nameText) {
                    top.linkTo(parent.top, margin = 12.dp)
                    start.linkTo(portraitImage.end, 12.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = artist.lifeYearsString,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(lifeYearsText) {
                    top.linkTo(nameText.bottom)
                    start.linkTo(portraitImage.end, margin = 12.dp)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}