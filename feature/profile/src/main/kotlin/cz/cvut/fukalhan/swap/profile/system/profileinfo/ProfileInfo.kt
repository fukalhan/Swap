package cz.cvut.fukalhan.swap.profile.system

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.ProfileViewModel

@Composable
fun ProfileInfo(viewModel: ProfileViewModel) {
    val profileState by viewModel.profileState.collectAsState()

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(SwapAppTheme.dimensions.sidePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            profileState.profilePicUri?.let {
                ProfilePicture(it)
            }
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                UsernameView(profileState.username)
                JoinDateView {
                    profileState.joinDate()
                }
            }
        }
    }
}

@Composable
fun ProfilePicture(pictureUri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pictureUri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.placeholder),
        contentDescription = "Profile picture",
        modifier = Modifier
            .clip(CircleShape)
            .size(130.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun UsernameView(text: String) {
    Text(
        text = text,
        style = SwapAppTheme.typography.titleSecondary,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    )
}

@Composable
fun JoinDateView(textRes: @Composable () -> String) {
    Text(
        text = textRes(),
        style = SwapAppTheme.typography.body,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    )
}
