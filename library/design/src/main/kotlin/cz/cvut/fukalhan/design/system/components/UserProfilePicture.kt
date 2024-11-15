package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.model.UserProfilePictureVo

@Composable
fun UserProfilePicture(
    model: UserProfilePictureVo,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = model.uri)
            .placeholder(R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .size(size = model.size),
        contentScale = ContentScale.Crop
    )
}