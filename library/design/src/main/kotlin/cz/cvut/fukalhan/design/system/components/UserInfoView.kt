package cz.cvut.fukalhan.design.system.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun UserInfoView(
    uri: Uri,
    username: String,
    joinDate: String,
    rating: Float,
    clickEnabled: Boolean,
    onClick: () -> Unit = {},
    additionalContent: (@Composable RowScope.() -> Unit)? = null
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(clickEnabled, onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(SwapAppTheme.dimensions.smallSidePadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(uri)
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))
            Column {
                InfoView(text = username, style = SwapAppTheme.typography.titleSecondary)
                InfoView(text = joinDate, style = SwapAppTheme.typography.body)
                RatingView(rating)
            }

            // Display additional content like buttons etc
            additionalContent?.invoke(this)
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

@Composable
fun InfoView(text: String, style: TextStyle) {
    Text(
        text = text,
        style = style,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
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

    Row(
        modifier = Modifier.padding(
            start = SwapAppTheme.dimensions.smallSidePadding,
            top = SwapAppTheme.dimensions.smallSidePadding
        )
    ) {
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
