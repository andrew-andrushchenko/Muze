package com.andrii_a.muze.ui.artists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.andrii_a.muze.R
import com.andrii_a.muze.domain.models.Artist
import com.andrii_a.muze.ui.util.endOffsetForPage
import com.andrii_a.muze.ui.util.offsetForPage
import com.andrii_a.muze.ui.util.startOffsetForPage
import kotlinx.coroutines.flow.Flow
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ArtistsScreen(
    artistsFlow: Flow<PagingData<Artist>>,
    navigateToArtistDetail: (artistId: Int) -> Unit
) {
    val artists = artistsFlow.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(initialPage = 0) { artists.itemCount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.artists),
                        modifier = Modifier.padding(16.dp)
                    )
                },
                actions = {
                    CircleIndicator(
                        state = pagerState,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 80.dp)
        ) {

            var offsetY by remember { mutableFloatStateOf(0f) }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .pointerInteropFilter { motionEvent ->
                        offsetY = motionEvent.y
                        false
                    }
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black),
            ) { pageIndex ->
                val artist = artists[pageIndex]

                Box(
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

                        },
                    contentAlignment = Alignment.Center,
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
        }
    }
}

class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
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
