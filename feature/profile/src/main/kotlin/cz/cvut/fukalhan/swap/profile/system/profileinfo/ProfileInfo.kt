package cz.cvut.fukalhan.swap.profile.system.profileinfo

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.Failure
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.Loading
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.ProfileInfoState
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.ProfileInfoViewModel
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.Success
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import org.koin.androidx.compose.getKoin

@Composable
fun ProfileInfo(viewModel: ProfileInfoViewModel) {
    val profileInfoState by viewModel.profileInfoState.collectAsState()
    val chatToken by viewModel.chatToken.collectAsState()

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .height(150.dp),
    ) {
        ResolveState(profileInfoState, chatToken)
    }
}

@Composable
fun ResolveState(
    state: ProfileInfoState,
    chatToken: String
) {
    when (state) {
        is Loading -> LoadingView()
        is Success -> {
            ProfileInfoContent(state)
            InitChatClient(getKoin().get(), chatToken, state)
        }
        is Failure -> FailureView(state.message)
        else -> {}
    }
}

@Composable
fun InitChatClient(chatClient: ChatClient, chatToken: String, state: Success) {
    if (chatToken.isNotEmpty()) {
        val user = User(
            id = state.id,
            name = state.username,
            image = state.profilePicUri.toString()
        )

        chatClient.connectUser(user = user, token = chatToken)
            .enqueue { result ->
                if (result.isSuccess) {
                    Log.e("ChatClient", "success")
                } else {
                    Log.e("ChatClient", "fail")
                }
            }
    }
}

@Composable
fun ProfileInfoContent(state: Success) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(SwapAppTheme.dimensions.sidePadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ProfilePicture(state.profilePicUri)

        Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            InfoView(state.username, SwapAppTheme.typography.titleSecondary)
            InfoView(state.joinDate, SwapAppTheme.typography.body)
        }
    }
}

@Composable
fun ProfilePicture(pictureUri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pictureUri)
            .placeholder(R.drawable.placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(130.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun InfoView(text: String, style: TextStyle) {
    Text(
        text = text,
        style = style,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    )
}
