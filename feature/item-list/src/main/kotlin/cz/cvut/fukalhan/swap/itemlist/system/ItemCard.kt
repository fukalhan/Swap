package cz.cvut.fukalhan.swap.itemlist.system

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemState

@Composable
fun ItemCard(itemState: ItemState) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ItemPicture(itemState.imageUri)

            Text(
                text = itemState.name,
                style = SwapAppTheme.typography.titleSecondary,
                modifier = Modifier.padding(SwapAppTheme.dimensions.sidePadding)
            )
        }
    }
}

@Composable
fun ItemPicture(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}
