package com.andrii_a.muze.ui.artists

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.andrii_a.muze.R
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.ui.common.EmptyContentBanner
import com.andrii_a.muze.ui.common.ErrorBanner
import com.andrii_a.muze.ui.util.endOffsetForPage
import com.andrii_a.muze.ui.util.indicatorOffsetForPage
import com.andrii_a.muze.ui.util.lifeYearsString
import com.andrii_a.muze.ui.util.offsetForPage
import com.andrii_a.muze.ui.util.startOffsetForPage
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ArtistsPager(
    artistItems: LazyPagingItems<Artist>,
    pagerState: PagerState,
    navigateToArtistDetail: (artistId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetY by remember { mutableFloatStateOf(0f) }

    when (artistItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            if (artistItems.itemCount > 0) {
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier
                        .pointerInteropFilter { motionEvent ->
                            offsetY = motionEvent.y
                            false
                        }
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black),
                ) { pageIndex ->
                    val artist = artistItems[pageIndex]

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .graphicsLayer {
                                val pageOffset = pagerState.offsetForPage(pageIndex)
                                translationX = size.width * pageOffset

                                val endOffset = pagerState.endOffsetForPage(pageIndex)

                                shadowElevation = 20f
                                shape = CirclePath(
                                    progress = 1f - endOffset.absoluteValue,
                                    origin = Offset(
                                        size.width,
                                        offsetY,
                                    )
                                )
                                clip = true

                                val absoluteOffset = pagerState.offsetForPage(pageIndex).absoluteValue
                                val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                                scaleX = scale
                                scaleY = scale

                                val startOffset = pagerState.startOffsetForPage(pageIndex)
                                alpha = (2f - startOffset) / 2f
                            }
                    ) {
                        artist?.let {
                            ArtistItem(
                                artist = artist,
                                onClick = { navigateToArtistDetail(artist.id) },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            } else {
                EmptyContentBanner(modifier = Modifier.fillMaxSize())
            }
        }

        is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is LoadState.Error -> {
            ErrorBanner(
                onRetry = artistItems::retry,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ArtistItem(
    artist: Artist,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artist.portraitImage.url)
                .crossfade(durationMillis = 1000)
                .scale(Scale.FILL)
                .placeholder(ColorDrawable(Color.Gray.toArgb()))
                .build(),
            contentDescription = stringResource(id = R.string.artist_portrait),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(width = 300.dp, height = 400.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(.8f)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = .7f),
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = artist.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )

            Text(
                text = artist.lifeYearsString,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircleIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        for (i in 0 until state.pageCount) {
            val offset = state.indicatorOffsetForPage(i)
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(lerp(6.dp, 16.dp, offset))
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = CircleShape,
                        )
                )
            }
        }
    }
}

private class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {

        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = (sqrt(
            size.height * size.height + size.width * size.width
        ) * .5f) * progress

        return Outline.Generic(
            Path().apply {
                addOval(
                    Rect(
                        center = center,
                        radius = radius,
                    )
                )
            }
        )
    }
}