package cz.cvut.fukalhan.swap.review.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ButtonRow
import cz.cvut.fukalhan.design.system.components.DescriptionView
import cz.cvut.fukalhan.design.system.components.InputFieldView
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.components.screenstate.SuccessSnackMessage
import cz.cvut.fukalhan.design.theme.semiTransparentBlack
import cz.cvut.fukalhan.swap.review.R
import cz.cvut.fukalhan.swap.review.presentation.AddReviewState
import cz.cvut.fukalhan.swap.review.presentation.ReviewViewModel
import cz.cvut.fukalhan.swap.review.presentation.UserInfoState

const val REVIEW_CHAR_LIMIT = 150
const val MIN_RATING = 1
const val MAX_RATING = 5

@Composable
fun AddReviewScreen(
    userId: String,
    viewModel: ReviewViewModel,
    onScreenInit: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit,
    navigateToProfileDetail: (String) -> Unit
) {
    val userInfoState by viewModel.userInfoState.collectAsState()
    val addReviewState by viewModel.addReviewState.collectAsState()

    val effect = remember {
        { viewModel.getUserInfo(userId) }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    TopBar(onScreenInit, onNavigateBack)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveUserInfoState(userInfoState, onNavigateBack, viewModel, navigateToProfileDetail)
        ResolveAddReviewState(addReviewState, onNavigateBack) {
            viewModel.resetAddReviewState()
        }
    }
}

@Composable
fun ResolveUserInfoState(
    state: UserInfoState,
    onNavigateBack: () -> Unit,
    viewModel: ReviewViewModel,
    navigateToProfileDetail: (String) -> Unit
) {
    when (state) {
        is UserInfoState.Loading -> LoadingView(semiTransparentBlack)
        is UserInfoState.Success -> ReviewScreenContent(state, onNavigateBack, viewModel, navigateToProfileDetail)
        is UserInfoState.Failure -> FailureView(state.message)
        else -> {}
    }
}

@Composable
fun ReviewScreenContent(
    state: UserInfoState.Success,
    onNavigateBack: () -> Unit,
    viewModel: ReviewViewModel,
    navigateToProfileDetail: (String) -> Unit
) {
    var selectedRating by remember { mutableStateOf(0) }
    var reviewDescription by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UserInfoView(
            state.profilePic,
            state.username,
            state.joinDate,
            state.rating,
            true,
            onClick = {
                navigateToProfileDetail(state.id)
            }
        )
        Column(
            modifier = Modifier
                .padding(top = SwapAppTheme.dimensions.sidePadding)
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            RatingView(selectedRating) {
                selectedRating = it
            }
            InputFieldView(
                R.string.reviewDescription,
                SwapAppTheme.dimensions.sidePadding
            ) {
                DescriptionView(
                    label = R.string.reviewDescriptionPlaceholder,
                    charLimit = REVIEW_CHAR_LIMIT,
                    description = reviewDescription
                ) {
                    reviewDescription = it
                }
            }
        }
        ButtonRow(onCancelClick = onNavigateBack) {
            Firebase.auth.currentUser?.let { user ->
                viewModel.addReview(state.id, user.uid, selectedRating, reviewDescription)
            }
        }
    }
}

@Composable
fun RatingView(
    selectedRating: Int,
    onSelectRating: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        for (i in MIN_RATING..MAX_RATING) {
            val filled = i <= selectedRating
            IconButton(
                onClick = { onSelectRating(i) },
                modifier = Modifier.size(SwapAppTheme.dimensions.icon)
            ) {
                Icon(
                    painter = if (filled) painterResource(R.drawable.filled_star) else painterResource(R.drawable.star),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun ResolveAddReviewState(
    state: AddReviewState,
    navigateBack: () -> Unit,
    resetAddReviewState: () -> Unit
) {
    when (state) {
        is AddReviewState.Loading -> LoadingView(semiTransparentBlack)
        is AddReviewState.Success -> OnSuccess(state.message, navigateBack, resetAddReviewState)
        is AddReviewState.Failure -> FailSnackMessage(state.message)
        else -> {}
    }
}

@Composable
fun OnSuccess(
    message: Int,
    navigateBack: () -> Unit,
    resetAddReviewState: () -> Unit
) {
    resetAddReviewState()
    SuccessSnackMessage(message)
    navigateBack()
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }

                Text(
                    text = stringResource(R.string.addReview),
                    style = SwapAppTheme.typography.screenTitle,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}
