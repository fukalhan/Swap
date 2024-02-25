package cz.cvut.fukalhan.swap.notifications.system

import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.theme.semiTransparentBlack
import cz.cvut.fukalhan.swap.notifications.R
import cz.cvut.fukalhan.swap.notifications.presentation.Failure
import cz.cvut.fukalhan.swap.notifications.presentation.Loading
import cz.cvut.fukalhan.swap.notifications.presentation.NewNotification
import cz.cvut.fukalhan.swap.notifications.presentation.NotificationState
import cz.cvut.fukalhan.swap.notifications.presentation.NotificationsState
import cz.cvut.fukalhan.swap.notifications.presentation.NotificationsViewModel

@Composable
fun Notifications(
    viewModel: NotificationsViewModel,
    navController: NavHostController,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit,
    onNotificationClick: (String) -> Unit
) {
    // Workaround to reset the new notifications count on back pressed
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backCallback = remember {
        object : OnBackPressedCallback(true) { // `true` indicates that the callback is enabled by default
            override fun handleOnBackPressed() {
                viewModel.resetNewNotificationsCount()
                navController.popBackStack()
            }
        }
    }
    SideEffect {
        onBackPressedDispatcher?.addCallback(backCallback)
    }

    val notifications by viewModel.notifications.collectAsState()
    val notificationsState by viewModel.notificationsState.collectAsState()

    TopBar(onScreenInit) {
        viewModel.resetNewNotificationsCount()
        navigateBack()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(
            notificationsState,
            setStateToInit = {
                viewModel.setStateToInit()
            }
        )
        NotificationsList(notifications, onNotificationClick)
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.onPrimary,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.notifications),
                    style = SwapAppTheme.typography.screenTitle,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun ResolveState(
    state: NotificationsState,
    setStateToInit: () -> Unit
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is NewNotification -> setStateToInit()
        is Failure -> {
            setStateToInit()
            FailSnackMessage(state.message)
        }
        else -> {}
    }
}

@Composable
fun NotificationsList(
    notifications: List<NotificationState>,
    onNotificationClick: (String) -> Unit
) {
    if (notifications.isEmpty()) {
        Text(
            stringResource(R.string.noData),
            style = SwapAppTheme.typography.titleSecondary,
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(notifications) {
                NotificationCard(it, onNotificationClick)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SwapAppTheme.dimensions.borderWidth)
                )
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: NotificationState,
    onNotificationClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onNotificationClick(notification.userId) }
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        UserProfilePic(notification.profilePic)
        Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.smallSpacer))
        Text(
            text = notification.notificationMessage,
            style = SwapAppTheme.typography.body,
        )
    }
}

@Composable
fun UserProfilePic(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .placeholder(cz.cvut.fukalhan.design.R.drawable.profile_pic_placeholder)
            .crossfade(true)
            .build(),
        placeholder = painterResource(cz.cvut.fukalhan.design.R.drawable.profile_pic_placeholder),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp),
        contentScale = ContentScale.Fit
    )
}
