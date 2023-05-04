package cz.cvut.fukalhan.swap.events.system.eventdetail

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.OrganizerInfoState
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.OrganizerInfoViewModel

@Composable
fun OrganizerInfo(
    userId: String,
    viewModel: OrganizerInfoViewModel,
    navigateToUserProfile: (String) -> Unit
) {
    val organizerInfoState by viewModel.organizerInfoState.collectAsState()
    val effect = remember {
        {
            viewModel.getOrganizerData(userId)
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier
            .padding(bottom = SwapAppTheme.dimensions.smallSidePadding)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(SwapAppTheme.dimensions.smallSidePadding)
        ) {
            Text(
                text = stringResource(id = R.string.organizer),
                style = SwapAppTheme.typography.titleSecondary,
                color = SwapAppTheme.colors.textPrimary,
            )

            ResolveState(organizerInfoState) {
                navigateToUserProfile(it)
            }
        }
    }
}

@Composable
fun ResolveState(
    state: OrganizerInfoState,
    onOrganizerClick: (String) -> Unit
) {
    when (state) {
        is OrganizerInfoState.Loading -> LoadingView()
        is OrganizerInfoState.Failure -> FailureView(state.message)
        is OrganizerInfoState.Success -> {
            UserInfo(
                state.organizer.id,
                state.organizer.profilePic,
                state.organizer.username,
                onOrganizerClick
            )
        }
        else -> Unit
    }
}

@Composable
fun UserInfo(
    id: String,
    profilePic: Uri,
    username: String,
    onUserClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onUserClick(id) }
            .padding(SwapAppTheme.dimensions.smallSidePadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserProfilePic(uri = profilePic)
        Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.smallSpacer))
        Text(
            text = username,
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary,
        )
    }
}

@Composable
fun UserProfilePic(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .placeholder(cz.cvut.fukalhan.design.R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(cz.cvut.fukalhan.design.R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp),
        contentScale = ContentScale.Crop
    )
}
