package cz.cvut.fukalhan.swap.messages.system

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import io.getstream.chat.android.client.models.Member

@Composable
fun ItemView(
    channelId: String,
    viewModel: ItemViewModel,
    onNavigateToItemDetail: (String) -> Unit,
    onNavigateToAddReview: (String) -> Unit,
    channelMembers: List<Member>
) {
    val itemState by viewModel.itemViewState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getItemData(channelId)
    }

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = SwapAppTheme.dimensions.sidePadding)
    ) {
        ResolveState(
            itemState,
            channelId,
            viewModel,
            onNavigateToItemDetail,
            onNavigateToAddReview,
            channelMembers
        )
    }
}

@Composable
fun ResolveState(
    state: ItemViewState,
    channelId: String,
    viewModel: ItemViewModel,
    onNavigateToItemDetail: (String) -> Unit,
    onNavigateToAddReview: (String) -> Unit,
    channelMembers: List<Member>
) {
    when (state) {
        is Loading -> LoadingView()
        is Success -> Item(state, channelId, viewModel, onNavigateToItemDetail, onNavigateToAddReview, channelMembers)
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
    onNavigateToItemDetail: (String) -> Unit,
    onNavigateToAddReview: (String) -> Unit,
    channelMembers: List<Member>
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
            onNavigateToItemDetail(itemState.id)
        }
        Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            Text(
                text = itemState.name,
                style = SwapAppTheme.typography.titleSecondary,
            )
            if (itemState.state == State.RESERVED || itemState.state == State.SWAPPED) {
                Text(
                    text = stringResource(itemState.state.label),
                    style = SwapAppTheme.typography.titleSecondary,
                )
            }

            ItemViewButton(itemState, channelId, viewModel, onNavigateToAddReview, channelMembers)
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
    onNavigateToAddReview: (String) -> Unit,
    channelMembers: List<Member>
) {
    val user = Firebase.auth.currentUser
    user?.let {
        val otherUser = channelMembers.first { member ->
            member.user.id != it.uid
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if (itemState.ownerId == it.uid || itemState.state == State.SWAPPED) {
                if (itemState.state == State.RESERVED) {
                    Button(
                        onClick = { viewModel.changeItemState(itemState.id, State.AVAILABLE, channelId) },
                        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.cancelReservation),
                            style = SwapAppTheme.typography.button,
                        )
                    }
                }

                Button(
                    onClick = {
                        when (itemState.state) {
                            State.AVAILABLE -> viewModel.changeItemState(itemState.id, State.RESERVED, channelId)
                            State.RESERVED -> viewModel.changeItemState(itemState.id, State.SWAPPED, channelId)
                            else -> { onNavigateToAddReview(otherUser.user.id) }
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
                    )
                }
            }
        }
    }
}
