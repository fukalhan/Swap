package cz.cvut.fukalhan.swap.profile.system.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemState

@Composable
fun LikeItemCard(
    itemState: ItemState,
    onLikeButtonClick: (Boolean) -> Unit
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.smallRoundCorners),
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ItemPicture(itemState.imageUri)
                LikeButton(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(SwapAppTheme.dimensions.smallSidePadding)
                        .size(SwapAppTheme.dimensions.icon)
                ) {
                    onLikeButtonClick(it)
                }
            }

            Text(
                modifier = Modifier
                    .padding(SwapAppTheme.dimensions.sidePadding)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                text = itemState.name,
                style = SwapAppTheme.typography.titleSecondary,
                maxLines = MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun LikeButton(
    modifier: Modifier,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    var isLiked by remember { mutableStateOf(true) }

    IconButton(
        onClick = {
            isLiked = !isLiked
            onLikeButtonClick(isLiked)
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(if (isLiked) R.drawable.colored_heart else R.drawable.heart),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
