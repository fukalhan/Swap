package cz.cvut.fukalhan.swap.messages.system

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.messages.R
import cz.cvut.fukalhan.swap.messages.presentation.ChangeItemStateFail
import cz.cvut.fukalhan.swap.messages.presentation.ChangeItemStateSuccess
import cz.cvut.fukalhan.swap.messages.presentation.Failure
import cz.cvut.fukalhan.swap.messages.presentation.ItemState
import cz.cvut.fukalhan.swap.messages.presentation.ItemStateChangeState
import cz.cvut.fukalhan.swap.messages.presentation.ItemViewModel
import cz.cvut.fukalhan.swap.messages.presentation.Loading
import cz.cvut.fukalhan.swap.messages.presentation.Success

@Composable
fun ItemView(
    channelId: String,
    viewModel: ItemViewModel
) {
    val itemState by viewModel.itemViewState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getItemData(channelId)
    }

    val changeItemState by viewModel.itemStateChangeState.collectAsState()

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(bottom = SwapAppTheme.dimensions.sidePadding)

    ) {
        Item(
            itemState,
            viewModel
        ) {
            // TODO add review screen
        }
        LoadingView(itemState)
        FailView(itemState)
        ResolveItemStateChange(
            changeItemState,
            setChangeStateToInit = { viewModel.setChangeStateToInit() }
        ) {
            viewModel.getItemData(channelId)
        }
    }
}

@Composable
fun Item(
    itemState: ItemState,
    viewModel: ItemViewModel,
    navigateToReviewScreen: () -> Unit
) {
    if (itemState is Success) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(SwapAppTheme.dimensions.smallSidePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ItemPicture(itemState.image)
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight()
            ) {
                Text(
                    text = itemState.name,
                    style = SwapAppTheme.typography.titleSecondary,
                    color = SwapAppTheme.colors.textPrimary
                )
                if (itemState.state == State.RESERVED || itemState.state == State.SWAPPED) {
                    Text(
                        text = stringResource(itemState.state.label),
                        style = SwapAppTheme.typography.titleSecondary,
                        color = SwapAppTheme.colors.textSecondary
                    )
                }

                ItemViewButton(itemState, viewModel, navigateToReviewScreen)
            }
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
        modifier = Modifier
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.smallRoundCorners))
            .size(100.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ItemViewButton(
    itemState: Success,
    viewModel: ItemViewModel,
    navigateToReviewScreen: () -> Unit
) {
    val user = Firebase.auth.currentUser
    user?.let {
        if (itemState.ownerId == it.uid || itemState.state == State.SWAPPED) {
            Button(
                onClick = {
                    when (itemState.state) {
                        State.AVAILABLE -> viewModel.changeItemState(itemState.id, State.RESERVED)
                        State.RESERVED -> viewModel.changeItemState(itemState.id, State.SWAPPED)
                        else -> navigateToReviewScreen()
                    }
                },
                colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary)
            ) {
                val label = when (itemState.state) {
                    State.AVAILABLE -> R.string.reserve
                    State.RESERVED -> R.string.markAsSwapped
                    else -> R.string.giveReview
                }

                Text(
                    text = stringResource(label),
                    style = SwapAppTheme.typography.button,
                    color = SwapAppTheme.colors.buttonText
                )
            }
        }
    }
}

@Composable
fun ResolveItemStateChange(
    itemStateChangeState: ItemStateChangeState,
    setChangeStateToInit: () -> Unit,
    updateItemView: () -> Unit
) {
    when (itemStateChangeState) {
        is ChangeItemStateFail -> {
            setChangeStateToInit()
            val context = LocalContext.current
            Toast.makeText(context, itemStateChangeState.message, Toast.LENGTH_SHORT).show()
        }
        is ChangeItemStateSuccess -> {
            setChangeStateToInit()
            updateItemView()
        }
        else -> {}
    }
}

@Composable
fun LoadingView(itemState: ItemState) {
    if (itemState is Loading) {
        Box(modifier = Modifier.wrapContentSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(SwapAppTheme.dimensions.icon),
                color = SwapAppTheme.colors.primary
            )
        }
    }
}

@Composable
fun FailView(itemState: ItemState) {
    if (itemState is Failure) {
        Text(
            text = stringResource(itemState.message),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )
    }
}
