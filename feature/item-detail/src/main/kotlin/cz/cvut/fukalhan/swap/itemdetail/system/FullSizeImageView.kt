package cz.cvut.fukalhan.swap.itemdetail.system

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemdetail.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullSizeImageView(
    visible: Boolean,
    images: List<Uri>,
    closeImagesView: () -> Unit
) {
    val pagerState = rememberPagerState()

    AnimatedVisibility(
        modifier = Modifier.zIndex(1f),
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            CancelIcon(
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopEnd),
                onClick = closeImagesView
            )

            HorizontalPager(
                pageCount = images.size,
                beyondBoundsPageCount = images.size,
                state = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()
            ) {
                ItemImage(uri = images[it], Modifier.wrapContentSize(), ContentScale.FillBounds)
            }

            ImageIndicator(
                images.size,
                pagerState,
                Modifier
                    .zIndex(1f)
                    .padding(SwapAppTheme.dimensions.sidePadding)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun CancelIcon(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(R.drawable.cancel),
        contentDescription = null,
        tint = SwapAppTheme.colors.backgroundSecondary,
        modifier = modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .padding(top = SwapAppTheme.dimensions.sidePadding)
            .size(SwapAppTheme.dimensions.icon)
            .clip(CircleShape)
            .background(Color.Black)
            .clickable(onClick = onClick)
    )
}
