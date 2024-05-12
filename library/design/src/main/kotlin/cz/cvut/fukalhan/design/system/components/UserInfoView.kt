package cz.cvut.fukalhan.design.system.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.UserInfoViewVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

@Composable
fun UserInfoView(
    modifier: Modifier = Modifier,
    model: UserInfoViewVo,
    onClick: (() -> Unit)? = null,
    onEndIconClick: (() -> Unit)? = null
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                enabled = onClick != null,
                onClick = { onClick?.invoke() }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            ProfilePicture(
                pictureUri = model.profilePicUri
            )

            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = model.username,
                    style = SwapAppTheme.typography.titleSecondary,
                )

                Text(
                    text = model.joinDate.getString(),
                    style = SwapAppTheme.typography.body
                )

                RatingView(
                    rating = model.rating
                )
            }

            model.endIcon?.let { icon ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(bottom = 20.dp),
                        backgroundColor = SwapAppTheme.colors.primary,
                        onClick = {
                            onEndIconClick?.invoke()
                        },
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = SwapAppTheme.colors.background
                        )
                    }
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
            .placeholder(R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(130.dp),
        contentScale = ContentScale.Crop
    )
}

const val MIN_RATING = 1
const val MAX_RATING = 5
const val HALF_STAR_VALUE = 0.5f

@Composable
fun RatingView(rating: Float) {
    val fullStars = rating.toInt()
    val halfStar = (rating - fullStars >= HALF_STAR_VALUE)
    val emptyStar = if (halfStar) MAX_RATING - (fullStars + MIN_RATING) else MAX_RATING - fullStars

    Row {
        repeat(fullStars) {
            Image(
                painter = painterResource(R.drawable.filled_star),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = SwapAppTheme.dimensions.smallSidePadding)
                    .size(20.dp)
            )
        }
        if (halfStar) {
            Image(
                painter = painterResource(R.drawable.half_star),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = SwapAppTheme.dimensions.smallSidePadding)
                    .size(20.dp)
            )
        }
        repeat(emptyStar) {
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

@Composable
@Preview
fun UserInfoViewPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        UserInfoView(
            model = UserInfoViewVo(
                username = "Username",
                profilePicUri = Uri.EMPTY,
                joinDate = StringModel.String("Joined on 12.5.2020"),
                rating = 4f,
                endIcon = R.drawable.ic_add
            )
        )

        UserInfoView(
            model = UserInfoViewVo(
                username = "Username",
                profilePicUri = Uri.EMPTY,
                joinDate = StringModel.String("Joined on 12.5.2020"),
                rating = 4f
            )
        )
    }

}
