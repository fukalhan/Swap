package cz.cvut.fukalhan.swap.itemlist.system.itemdetail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun ImageRow() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(SwapAppTheme.dimensions.imageView)
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
    ) {
    }
}

@Composable
fun ItemImage(uri: Uri) {
    AsyncImage(
        model = uri,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .border(
                width = SwapAppTheme.dimensions.borderWidth,
                color = SwapAppTheme.colors.component,
                shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
            )
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ImageIndicator(imagesSize: Int) {
    val scrollState = rememberScrollState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(imagesSize) { index ->
            Box(
                modifier = Modifier.run {
                    padding(SwapAppTheme.dimensions.smallSidePadding)
                        .size(SwapAppTheme.dimensions.smallSpacer)
                        .clip(CircleShape)
                        .background(
                            color = if (index == scrollState.value / 200) {
                                Color.White
                            } else {
                                Color.Gray
                            }
                        )
                }
            )
        }
    }
}
