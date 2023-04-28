package cz.cvut.fukalhan.swap.profiledetail.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.profiledetail.presentation.Failure
import cz.cvut.fukalhan.swap.profiledetail.presentation.Loading
import cz.cvut.fukalhan.swap.profiledetail.presentation.ProfileDetailState
import cz.cvut.fukalhan.swap.profiledetail.presentation.ProfileDetailViewModel
import cz.cvut.fukalhan.swap.profiledetail.presentation.Success

@Composable
fun ProfileDetailScreen(
    userId: String,
    viewModel: ProfileDetailViewModel,
    onInitScreen: (ScreenState) -> Unit,
    navigateBack: () -> Unit,
    navigateToProfileDetail: (String) -> Unit
) {
    val profileDetailState by viewModel.profileDetailState.collectAsState()
    val effect = remember {
        {
            viewModel.getUserProfile(userId)
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    TopBar(onInitScreen, navigateBack)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(profileDetailState, navigateToProfileDetail)
    }
}

@Composable
fun TopBar(
    onInitScreen: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit,
) {
    onInitScreen(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.profileDetail),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun ResolveState(
    state: ProfileDetailState,
    navigateToProfileDetail: (String) -> Unit
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> ProfileDetail(state, navigateToProfileDetail)
        is Failure -> FailureView(state.message)
        else -> {}
    }
}

@Composable
fun ProfileDetail(
    state: Success,
    navigateToProfileDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UserInfoView(
            state.user.profilePic,
            state.user.username,
            state.user.joinDate,
            state.user.rating,
            false
        )
        Bio(state.user.bio)
        ReviewList(
            state.reviews,
            Modifier
                .background(SwapAppTheme.colors.backgroundSecondary)
                .weight(1f)
                .fillMaxWidth(),
            navigateToProfileDetail
        )
    }
}
