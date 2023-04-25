package cz.cvut.fukalhan.swap.itemdetail.system

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ItemStateView
import cz.cvut.fukalhan.swap.itemdata.model.State

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageView(
    images: List<Uri>,
    itemState: State,
    onClick: () -> Unit
) {
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clickable { onClick() }
    ) {
        ItemStateView(
            itemState == State.RESERVED || itemState == State.SWAPPED,
            itemState.label,
            Modifier.align(Alignment.BottomCenter)
        )
        HorizontalPager(
            pageCount = images.size,
            beyondBoundsPageCount = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            ItemImage(uri = images[it], Modifier.fillMaxSize(), ContentScale.Crop)
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
