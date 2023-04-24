package cz.cvut.fukalhan.swap.messages.system

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.messages.R
import cz.cvut.fukalhan.swap.messages.presentation.ChangeStateFailure
import cz.cvut.fukalhan.swap.messages.presentation.Failure
import cz.cvut.fukalhan.swap.messages.presentation.ItemViewModel
import cz.cvut.fukalhan.swap.messages.presentation.ItemViewState
import cz.cvut.fukalhan.swap.messages.presentation.Loading
import cz.cvut.fukalhan.swap.messages.presentation.Success
import cz.cvut.fukalhan.swap.navigation.presentation.SecondaryScreen

@Composable
fun ItemView(
    channelId: String,
    viewModel: ItemViewModel,
    navController: NavHostController
) {
    val itemState by viewModel.itemViewState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getItemData(channelId)
    }

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(bottom = SwapAppTheme.dimensions.sidePadding)

    ) {
        ResolveState(
            itemState,
            channelId,
            viewModel,
            navController
        ) {
            // TODO add review screen
        }
    }
}

@Composable
fun ResolveState(
    state: ItemViewState,
    channelId: String,
    viewModel: ItemViewModel,
    navController: NavHostController,
    navigateToReviewScreen: () -> Unit
) {
    when (state) {
        is Loading -> LoadingView()
        is Success -> Item(state, channelId, viewModel, navController, navigateToReviewScreen)
        is Failure -> FailureView(state.message)
        is ChangeStateFailure -> {
            val context = LocalContext.current
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}

@Composable
fun Item(
    itemState: Success,
    channelId: String,
    viewModel: ItemViewModel,
    navController: NavHostController,
    navigateToReviewScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(SwapAppTheme.dimensions.smallSidePadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ItemPicture(itemState.image) {
            navController.navigate("${SecondaryScreen.ItemDetail.route}/${itemState.id}")
        }
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

            ItemViewButton(itemState, channelId, viewModel, navigateToReviewScreen)
        }
    }
}

@Composable
fun ItemPicture(
    uri: Uri,
    onClick: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.item_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.smallRoundCorners))
            .size(100.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ItemViewButton(
    itemState: Success,
    channelId: String,
    viewModel: ItemViewModel,
    navigateToReviewScreen: () -> Unit
) {
    val user = Firebase.auth.currentUser
    user?.let {
        if (itemState.ownerId == it.uid || itemState.state == State.SWAPPED) {
            Button(
                onClick = {
                    when (itemState.state) {
                        State.AVAILABLE -> viewModel.changeItemState(itemState.id, State.RESERVED, channelId)
                        State.RESERVED -> viewModel.changeItemState(itemState.id, State.SWAPPED, channelId)
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
