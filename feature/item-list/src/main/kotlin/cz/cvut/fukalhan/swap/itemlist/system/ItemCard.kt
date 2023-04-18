package cz.cvut.fukalhan.swap.itemlist.system

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemState

const val MAX_LINES = 1

@Composable
fun ItemCard(itemState: ItemState) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            ItemPicture(
                itemState.imageUri,
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Text(
                modifier = Modifier
                    .padding(SwapAppTheme.dimensions.sidePadding)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                text = itemState.name,
                style = SwapAppTheme.typography.titleSecondary,
                maxLines = MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ItemPicture(
    uri: Uri,
    modifier: Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.camera),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
