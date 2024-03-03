package cz.cvut.fukalhan.swap.profiledetail.system

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.profiledetail.presentation.review.ReviewListState
import cz.cvut.fukalhan.swap.profiledetail.presentation.review.ReviewListViewModel
import cz.cvut.fukalhan.swap.profiledetail.presentation.review.ReviewState
import cz.cvut.fukalhan.swap.profiledetail.system.user.Rating

const val MAX_LINES = 2

@Composable
fun Reviews(
    userId: String,
    viewModel: ReviewListViewModel,
    topDividerVisible: Boolean,
    navigateToProfileDetail: (String) -> Unit
) {
    val reviewListState by viewModel.reviewListState.collectAsState()
    val effect = remember {
        {
            viewModel.getReviews(userId)
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(reviewListState, topDividerVisible, navigateToProfileDetail)
    }
}

@Composable
fun ResolveState(
    state: ReviewListState,
    topDividerVisible: Boolean,
    navigateToProfileDetail: (String) -> Unit
) {
    when (state) {
        is ReviewListState.Loading -> LoadingView()
        is ReviewListState.DataLoaded -> ReviewList(state.reviews, topDividerVisible, navigateToProfileDetail)
        is ReviewListState.Empty -> EmptyView(state.message)
        is ReviewListState.Failure -> FailureView(state.message)
        else -> Unit
    }
}

@Composable
fun ReviewList(
    reviews: List<ReviewState>,
    topDividerVisible: Boolean,
    navigateToProfileDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (topDividerVisible) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SwapAppTheme.dimensions.borderWidth)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(reviews) {
                ReviewCard(it, navigateToProfileDetail)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SwapAppTheme.dimensions.borderWidth)
                )
            }
        }
    }
}

@Composable
fun ReviewCard(
    review: ReviewState,
    navigateToProfileDetail: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val modifier = if (expanded) Modifier.wrapContentHeight() else Modifier.height(90.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .clickable { expanded = !expanded }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserProfilePic(review.reviewerProfilePic) {
                navigateToProfileDetail(review.reviewerId)
            }
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.smallSpacer))
            Rating(review.rating)
        }
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
fun UserProfilePic(
    uri: Uri,
    navigateToProfileDetail: () -> Unit
) {
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
            .size(40.dp)
            .clickable(onClick = navigateToProfileDetail),
        contentScale = ContentScale.Crop
    )
}
