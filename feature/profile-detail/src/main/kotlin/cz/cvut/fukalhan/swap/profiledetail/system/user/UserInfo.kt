package cz.cvut.fukalhan.swap.profiledetail.system.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.profiledetail.presentation.user.UserInfoState
import cz.cvut.fukalhan.swap.profiledetail.presentation.user.UserInfoViewModel

const val MAX_RATING = 5

@Composable
fun UserInfo(
    userId: String,
    viewModel: UserInfoViewModel,
) {
    val userInfoState by viewModel.userInfoState.collectAsState()
    val effect = remember {
        {
            viewModel.getUserInfo(userId)
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ResolveState(userInfoState)
    }
}

@Composable
fun ResolveState(state: UserInfoState) {
    when (state) {
        is UserInfoState.Loading -> LoadingView()
        is UserInfoState.DataLoaded -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val user = state.user
                UserInfoView(
                    uri = user.profilePic,
                    username = user.username,
                    joinDate = user.joinDate,
                    rating = user.rating,
                    false,
                )

                Bio(state.user.bio)
            }
        }
        is UserInfoState.Failure -> FailureView(state.message)
        else -> {}
    }
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
        repeat(MAX_RATING - rating) {
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
