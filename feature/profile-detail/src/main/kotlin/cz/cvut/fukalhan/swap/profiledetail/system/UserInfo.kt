package cz.cvut.fukalhan.swap.profiledetail.system

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun UserProfilePic(
    uri: Uri,
    navigateToProfileDetail: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .placeholder(R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .clickable(onClick = navigateToProfileDetail),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Rating(rating: Int) {
    Row(
        modifier = Modifier.padding(
            start = SwapAppTheme.dimensions.smallSidePadding,
            top = SwapAppTheme.dimensions.smallSidePadding
        )
    ) {
        repeat(rating) {
            Image(
                painter = painterResource(R.drawable.filled_star),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = SwapAppTheme.dimensions.smallSidePadding)
                    .size(20.dp)
            )
        }
        repeat(5 - rating) {
            Image(
                painter = painterResource(R.drawable.star),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = SwapAppTheme.dimensions.smallSidePadding)
                    .size(20.dp)
            )
        }
    }
}
