package cz.cvut.fukalhan.swap.profiledetail.system

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.profiledetail.presentation.ReviewState

const val MAX_LINES = 2

@Composable
fun ReviewList(
    reviews: List<ReviewState>,
    modifier: Modifier,
    navigateToProfileDetail: (String) -> Unit
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = stringResource(R.string.reviews),
                style = SwapAppTheme.typography.titleSecondary,
                color = SwapAppTheme.colors.textPrimary,
                modifier = Modifier.padding(SwapAppTheme.dimensions.sidePadding)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SwapAppTheme.dimensions.borderWidth)
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
