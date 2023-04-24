package cz.cvut.fukalhan.swap.itemdetail.system

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.itemdetail.presentation.OwnerInfo

@Composable
fun ItemOwnerInfo(
    ownerInfo: OwnerInfo,
    onSendMessageButtonClick: () -> Unit
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
            ProfilePicture(ownerInfo.profilePic)
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))
            TextView(
                text = ownerInfo.username,
                style = SwapAppTheme.typography.titleSecondary
            )
            SendMessageButton(Modifier.weight(1f), onSendMessageButtonClick)
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

@Composable
fun SendMessageButton(
    modifier: Modifier,
    onSendMessageButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary),
            modifier = Modifier
                .wrapContentSize(),
            onClick = onSendMessageButtonClick
        ) {
            Text(
                text = stringResource(R.string.sendMessage),
                style = SwapAppTheme.typography.button,
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}
