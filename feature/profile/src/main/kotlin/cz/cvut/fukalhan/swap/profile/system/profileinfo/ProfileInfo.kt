package cz.cvut.fukalhan.swap.profile.system.profileinfo

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.Failure
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.Loading
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.ProfileInfoState
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.ProfileInfoViewModel
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.Success
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import org.koin.androidx.compose.getKoin

@Composable
fun ProfileInfo(
    viewModel: ProfileInfoViewModel,
    navigateToProfileDetail: (String) -> Unit
) {
    val profileInfoState by viewModel.profileInfoState.collectAsState()
    val chatToken by viewModel.chatToken.collectAsState()
    val effect = remember {
        {
            Firebase.auth.currentUser?.let {
                viewModel.initProfile(it.uid)
            }
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .padding(bottom = SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .height(150.dp),
    ) {
        ResolveState(profileInfoState, chatToken, navigateToProfileDetail)
    }
}

@Composable
fun ResolveState(
    state: ProfileInfoState,
    chatToken: String,
    navigateToProfileDetail: (String) -> Unit
) {
    when (state) {
        is Loading -> LoadingView()
        is Success -> {
            UserInfoView(
                uri = state.profilePicUri,
                username = state.username,
                joinDate = state.joinDate,
                rating = state.rating,
                true,
                onClick = {
                    navigateToProfileDetail(state.id)
                }
            )
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
