package com.andrii_a.muze.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.andrii_a.muze.R
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopLayout(
    listState: LazyListState,
    contentPadding: PaddingValues,
    list: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    Box {
        list()

        val showButton = remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        AnimatedVisibility(
            visible = showButton.value,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(contentPadding)
        ) {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = stringResource(id = R.string.to_top))
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = stringResource(id = R.string.to_top)
                    )
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                onClick = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }
    }
}

@Composable
fun LoadingListItem(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.TopCenter) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_list_item_animation))

        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE,
            modifier = modifier
                .requiredSize(64.dp)
                .scale(2f, 2f)
        )
    }
}

@Composable
fun EmptyContentBanner(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.empty_content_banner_text)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                if (isSystemInDarkTheme()) R.raw.empty_animation_dark
                else R.raw.empty_animation_light
            )
        )

        LottieAnimation(
            composition = composition,
            iterations = 1,
            modifier = Modifier
                .requiredSize(250.dp)
                .scale(1.6f, 1.6f)
        )

        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )
    }
}

@Composable
fun ErrorBanner(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.error_banner_text),
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                if (isSystemInDarkTheme()) R.raw.error_animation_dark
                else R.raw.error_animation_light
            )
        )

        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE,
            modifier = Modifier
                .requiredSize(250.dp)
                .scale(1.3f, 1.3f)
        )

        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )

        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.error_loading_items),
    onRetry: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
        ) {
            Text(
                text = message,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.6f)
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.weight(0.2f)
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}