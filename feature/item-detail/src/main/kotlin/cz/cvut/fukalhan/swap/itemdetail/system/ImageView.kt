package cz.cvut.fukalhan.swap.itemdetail.system

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.itemdetail.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageRow(
    images: List<Uri>,
    itemState: State
) {
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        StateView(itemState, Modifier.align(Alignment.BottomCenter))
        HorizontalPager(
            pageCount = images.size,
            beyondBoundsPageCount = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            ItemImage(uri = images[it])
        }

        ImageIndicator(
            images.size,
            pagerState,
            Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun StateView(state: State, modifier: Modifier) {
    if (state == State.RESERVED || state == State.SWAPPED) {
        Row(
            modifier = modifier
                .background(semiTransparentBlack)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(SwapAppTheme.dimensions.smallSidePadding)
                .zIndex(1f)
        ) {
            Text(
                text = stringResource(state.label),
                style = SwapAppTheme.typography.button,
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}

@Composable
fun ItemImage(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.item_placeholder),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageIndicator(
    size: Int,
    pagerState: PagerState,
    modifier: Modifier,
) {
    if (size > 1) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
            }
        }
    }
}
