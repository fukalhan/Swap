package cz.cvut.fukalhan.swap.itemlist.system

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemState

const val MAX_LINES = 1

@Composable
fun ItemCard(
    itemState: ItemState,
    navigateToItemDetail: (String) -> Unit,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .clickable {
                navigateToItemDetail(itemState.id)
            }
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
                    false,
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
fun ItemPicture(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.item_placeholder),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LikeButton(
    isLiked: Boolean,
    modifier: Modifier,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    var liked by remember { mutableStateOf(isLiked) }

    IconButton(
        onClick = {
            liked = !liked
            onLikeButtonClick(liked)
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(if (liked) R.drawable.colored_heart else R.drawable.heart),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
