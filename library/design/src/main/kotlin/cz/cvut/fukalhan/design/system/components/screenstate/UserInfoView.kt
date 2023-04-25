package cz.cvut.fukalhan.design.system.components.screenstate

import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun UserInfoView(
    uri: Uri,
    username: String,
    additionalContent: (@Composable RowScope.() -> Unit)? = null
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SwapAppTheme.dimensions.smallSidePadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(uri)
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))
            Text(
                text = username,
                style = SwapAppTheme.typography.titleSecondary,
                color = SwapAppTheme.colors.textPrimary
            )

            // Display additional content like buttons etc
            additionalContent?.invoke(this)
        }
    }
}

@Composable
fun ProfilePicture(pictureUri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pictureUri)
            .placeholder(R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(100.dp),
        contentScale = ContentScale.Fit
    )
}
