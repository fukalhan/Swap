package cz.cvut.fukalhan.swap.itemdetail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.UserInfoView
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.itemdetail.model.ItemDetailScreenData
import cz.cvut.fukalhan.swap.itemdetail.model.ItemDetailScreenEvent
import cz.cvut.fukalhan.swap.itemdetail.model.OwnerInfoVo

/**
 * Screen to display the item detail
 *
 * @param viewModel view model handling this view
 * @param onNavigateBack on navigate back click action
 * @param navigateToOwnerProfileDetail on owner profile click action
 * @param navigateToChat navigate action to the newly created chat between the item owner and the user
 */
@Composable
fun ItemDetailScreen(
    viewModel: ComposeViewModel<ItemDetailScreenData, ItemDetailScreenEvent>,
    onNavigateBack: () -> Unit,
    navigateToOwnerProfileDetail: (String) -> Unit,
    navigateToChat: (String) -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    ScreenContentWrapper(
        state = viewState
    ) {
        ItemDetailContent(
            data = viewState.data,
            onLikeButtonClick = {
                viewModel.onEvent(ItemDetailScreenEvent.OnLikeClick(isLiked = it))
            },
            createChatChannel = {
                viewModel.onEvent(ItemDetailScreenEvent.CreateChatChannel)
            },
            navigateToOwnerProfileDetail = navigateToOwnerProfileDetail
        )
    }
}

@Composable
private fun ItemDetailContent(
    data: ItemDetailScreenData,
    onLikeButtonClick: (Boolean) -> Unit,
    createChatChannel: () -> Unit,
    navigateToOwnerProfileDetail: (String) -> Unit
) {
    val isUserTheOwner = Firebase.auth.currentUser?.let {
        it.uid == data.ownerInfoVo.id
    } ?: false
    var isFullSizeImageViewVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FullSizeImageView(
            visible = isFullSizeImageViewVisible,
            images = data.images,
            closeImagesView = {
                isFullSizeImageViewVisible = false
            }
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ImageView(
                images = data.images,
                itemState = data.state,
                onClick = {
                    isFullSizeImageViewVisible = true
                }
            )

            ItemInfo(
                modifier = Modifier.weight(1f),
                name = data.name,
                description = data.description,
                category = data.category,
                isLiked = data.isLiked,
                displayLikeButton = !isUserTheOwner,
                onLikeButtonClick = onLikeButtonClick
            )

            if (!isUserTheOwner) {
                UserInfoView(
                    uri = data.ownerInfoVo.profilePic,
                    username = data.ownerInfoVo.username,
                    joinDate = data.ownerInfoVo.joinDate,
                    rating = data.ownerInfoVo.rating,
                    clickEnabled = true,
                    onClick = {
                        navigateToOwnerProfileDetail(data.ownerInfoVo.id)
                    },
                    additionalContent = {
                        SendMessageButton(
                            modifier = Modifier.weight(1f),
                            onClick = createChatChannel
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ItemInfo(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    category: Category,
    isLiked: Boolean,
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
                    text = name,
                    style = SwapAppTheme.typography.titlePrimary,
                )
                Text(
                    text = stringResource(category.labelId),
                    style = SwapAppTheme.typography.titleSecondary,
                )
            }

            if (displayLikeButton) {
                LikeButton(
                    isLiked = isLiked,
                    onClick = onLikeButtonClick
                )
            }
        }

        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))

        Text(
            text = description,
            style = SwapAppTheme.typography.body,
        )

        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))
    }
}

@Composable
private fun LikeButton(
    isLiked: Boolean,
    onClick: (Boolean) -> Unit,
) {
    IconButton(
        onClick = {
            onClick(!isLiked)
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
private fun SendMessageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Surface(
            shape = CircleShape,
            color = SwapAppTheme.colors.primary,
            elevation = SwapAppTheme.dimensions.elevation,
            modifier = Modifier.wrapContentSize()
        ) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SwapAppTheme.colors.primary)
                    .wrapContentSize(),
                onClick = onClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.message),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
@Preview
fun ItemDetailScreenPreview() {
    ItemDetailScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = ItemDetailScreenData(
                    name = "Books",
                    description = "Books for sale",
                    isLiked = true,
                    ownerInfoVo = OwnerInfoVo(
                        username = "User",
                        joinDate = StringModel.String("Joined 25.6.2020"),
                        rating = 4.5f

                    )
                )
            )
        ),
        onNavigateBack = {},
        navigateToOwnerProfileDetail = {},
        navigateToChat = {}
    )
}