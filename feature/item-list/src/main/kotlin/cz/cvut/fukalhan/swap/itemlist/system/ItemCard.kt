package cz.cvut.fukalhan.swap.itemlist.system

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ItemCard
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemState

const val MAX_LINES = 1

@Composable
fun ItemCard(
    itemState: ItemState,
    navigateToItemDetail: (String) -> Unit,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    ItemCard(onClick = { navigateToItemDetail(itemState.id) }) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ItemPicture(itemState.imageUri)
            StateView(itemState.state, Modifier.align(Alignment.TopCenter))
            LikeButton(
                itemState,
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
fun StateView(state: State, modifier: Modifier) {
    if (state == State.RESERVED || state == State.SWAPPED) {
        Row(
            modifier = modifier
                .background(semiTransparentBlack)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(SwapAppTheme.dimensions.smallSidePadding)
                .zIndex(1f)
        ) {
            Text(
                text = stringResource(state.label),
                style = SwapAppTheme.typography.button,
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}

@Composable
fun LikeButton(
    itemState: ItemState,
    modifier: Modifier,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    var isLiked by remember { mutableStateOf(itemState.isLiked) }

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
