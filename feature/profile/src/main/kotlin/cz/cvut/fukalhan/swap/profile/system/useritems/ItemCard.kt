package cz.cvut.fukalhan.swap.profile.system.useritems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R

@Composable
fun ItemCard() {
    Column(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
    ) {
        ItemPicture()

        Text(
            text = "Item name",
            style = SwapAppTheme.typography.body,
            modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
        )
        Text(
            text = "Location",
            style = SwapAppTheme.typography.body,
            modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
        )
    }
}

@Composable
fun ItemPicture() {
    Image(
        painter = painterResource(R.drawable.picture_placeholder),
        contentDescription = "Item picture",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillWidth
    )
    /*AsyncImage(
        model = null,
        placeholder = painterResource(R.drawable.picture_placeholder),
        contentDescription = "Profile picture"
    )*/
}
