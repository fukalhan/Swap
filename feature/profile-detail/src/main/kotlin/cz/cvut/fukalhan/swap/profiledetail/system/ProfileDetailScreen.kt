package cz.cvut.fukalhan.swap.profiledetail.system

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.profiledetail.presentation.Failure
import cz.cvut.fukalhan.swap.profiledetail.presentation.Loading
import cz.cvut.fukalhan.swap.profiledetail.presentation.ProfileDetailState
import cz.cvut.fukalhan.swap.profiledetail.presentation.ProfileDetailViewModel
import cz.cvut.fukalhan.swap.profiledetail.presentation.ReviewState
import cz.cvut.fukalhan.swap.profiledetail.presentation.Success

const val MAX_LINES = 2

@Composable
fun ProfileDetailScreen(
    userId: String,
    viewModel: ProfileDetailViewModel,
    onInitScreen: (ScreenState) -> Unit,
    navigateBack: () -> Unit
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
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(bottom = SwapAppTheme.dimensions.bottomScreenPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(profileDetailState)
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
fun ResolveState(state: ProfileDetailState) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> ProfileDetail(state)
        is Failure -> FailureView(state.message)
        else -> {}
    }
}

@Composable
fun ProfileDetail(state: Success) {
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
        ReviewList(
            state.reviews,
            Modifier.weight(1f)
        )
    }
}

@Composable
fun Bio(bio: String) {
    Text(
        text = stringResource(R.string.bio),
        style = SwapAppTheme.typography.titleSecondary,
        color = SwapAppTheme.colors.textPrimary,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    )
    Text(
        text = bio,
        style = SwapAppTheme.typography.body,
        color = SwapAppTheme.colors.textSecondary,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    )
}

@Composable
fun ReviewList(
    reviews: List<ReviewState>,
    modifier: Modifier
) {
    Text(
        text = stringResource(R.string.reviews),
        style = SwapAppTheme.typography.titleSecondary,
        color = SwapAppTheme.colors.textPrimary,
        modifier = Modifier.padding(SwapAppTheme.dimensions.sidePadding)
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (reviews.isEmpty()) {
            EmptyView(message = R.string.noReviewsToDisplay)
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(reviews) {
                    ReviewCard(it)
                }
            }
        }
    }
}

@Composable
fun ReviewCard(review: ReviewState) {
    var expanded by remember { mutableStateOf(false) }
    val modifier = if (expanded) Modifier.wrapContentHeight() else Modifier.height(100.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .clickable { expanded = !expanded }
    ) {
        Rating(review.rating)
        Text(
            modifier = Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .wrapContentHeight()
                .fillMaxWidth(),
            text = review.description,
            style = SwapAppTheme.typography.body,
            maxLines = MAX_LINES,
            overflow = TextOverflow.Ellipsis
        )
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
                painter = painterResource(cz.cvut.fukalhan.design.R.drawable.filled_star),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = SwapAppTheme.dimensions.smallSidePadding)
                    .size(20.dp)
            )
        }
        repeat(5 - rating) {
            Image(
                painter = painterResource(cz.cvut.fukalhan.design.R.drawable.star),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = SwapAppTheme.dimensions.smallSidePadding)
                    .size(20.dp)
            )
        }
    }
}
