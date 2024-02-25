package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.model.CarouselVo
import cz.cvut.fukalhan.design.system.model.ImagePagerVo
import cz.cvut.fukalhan.design.system.model.ItemImageVo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    imagePagerVo: ImagePagerVo,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        imagePagerVo.images.size
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
        ) {
            ItemImage(
                itemImageVo = ItemImageVo(
                    uri = imagePagerVo.images[it],
                    contentScale = if (imagePagerVo.isFullSizeView) ContentScale.Crop else ContentScale.FillBounds,
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }

        Carousel(
            carouselVo = CarouselVo(
                size = imagePagerVo.images.size,
                currentPage = pagerState.currentPage
            ),
            modifier = Modifier
                .zIndex(1f)
                .padding(SwapAppTheme.dimensions.sidePadding)
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}
