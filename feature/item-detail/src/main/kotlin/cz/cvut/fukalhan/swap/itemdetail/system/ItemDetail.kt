package cz.cvut.fukalhan.swap.itemdetail.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.itemdetail.presentation.ItemDetailViewModel
import cz.cvut.fukalhan.swap.itemdetail.presentation.Success

@Composable
fun ItemDetail(
    itemDetailState: Success,
    viewModel: ItemDetailViewModel
) {
    val user = Firebase.auth.currentUser
    val isUserTheOwner = user?.uid == itemDetailState.ownerInfo.id
    Column(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .fillMaxSize()
    ) {
        ImageRow(itemDetailState.images, itemDetailState.state)
        ItemInfo(
            itemDetailState,
            Modifier.weight(1f),
            !isUserTheOwner
        ) { isLiked ->
            user?.let { user ->
                viewModel.toggleItemLike(user.uid, itemDetailState.id, isLiked)
            }
        }
        if (!isUserTheOwner) {
            ItemOwnerInfo(itemDetailState.ownerInfo) {
                user?.let { user ->
                    viewModel.createChannel(
                        user.uid,
                        itemDetailState.ownerInfo.id,
                        itemDetailState.id,
                        itemDetailState.images.first(),
                        itemDetailState.name
                    )
                }
            }
        }
    }
}

@Composable
fun ItemInfo(
    itemDetailState: Success,
    modifier: Modifier,
    displayLikeButton: Boolean,
    onLikeButtonClick: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(SwapAppTheme.dimensions.sidePadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Text(
                    text = itemDetailState.name,
                    style = SwapAppTheme.typography.titlePrimary,
                    color = SwapAppTheme.colors.textPrimary,
                )
                TextView(stringResource(itemDetailState.category.labelId), SwapAppTheme.typography.titleSecondary)
            }
            if (displayLikeButton) {
                LikeButton(itemDetailState, onLikeButtonClick)
            }
        }
        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))
        TextView(itemDetailState.description, SwapAppTheme.typography.body)
        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))
    }
}

@Composable
fun LikeButton(
    itemDetailState: Success,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    var isLiked by remember { mutableStateOf(itemDetailState.isLiked) }

    IconButton(
        onClick = {
            isLiked = !isLiked
            onLikeButtonClick(isLiked)
        },
        modifier = Modifier
            .padding(start = SwapAppTheme.dimensions.smallSidePadding)
            .size(50.dp)
    ) {
        Icon(
            painter = painterResource(if (isLiked) R.drawable.colored_heart else R.drawable.heart),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun TextView(text: String, style: TextStyle) {
    Text(
        text = text,
        style = style,
        color = SwapAppTheme.colors.textPrimary
    )
}
