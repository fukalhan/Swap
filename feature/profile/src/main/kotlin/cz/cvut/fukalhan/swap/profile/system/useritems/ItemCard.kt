package cz.cvut.fukalhan.swap.profile.system.useritems

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.useritems.ItemState

@Composable
fun ItemCard(itemState: ItemState) {
    Column(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
    ) {
        ItemPicture(itemState.imageUri)

        Text(
            text = itemState.name,
            style = SwapAppTheme.typography.titleSecondary,
        )
    }
}

@Composable
fun ItemPicture(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.picture_placeholder),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}
