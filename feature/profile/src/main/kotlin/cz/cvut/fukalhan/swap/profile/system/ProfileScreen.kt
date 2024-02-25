package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.swap.notifications.presentation.NotificationsViewModel
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.system.items.ItemsView
import cz.cvut.fukalhan.swap.profile.system.profileinfo.ProfileInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: NotificationsViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateToNotifications: () -> Unit,
    onSettingsClick: () -> Unit,
    navigateToProfileDetail: (String) -> Unit,
    navigateToItemDetail: (String) -> Unit
) {
    val newNotifications by viewModel.newNotificationsCount.collectAsState()

    TopBar(
        onScreenInit,
        {
            viewModel.resetNewNotificationsCount()
            navigateToNotifications()
        },
        onSettingsClick,
        newNotifications,
    )

    Profile(navigateToProfileDetail, navigateToItemDetail)
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    navigateToNotifications: () -> Unit,
    onSettingClick: () -> Unit,
    newNotificationsCount: Int,
) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.profile),
                style = SwapAppTheme.typography.screenTitle,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                ) {
                    IconButton(onClick = navigateToNotifications) {
                        Icon(
                            painter = painterResource(R.drawable.notifications),
                            contentDescription = null,
                            tint = SwapAppTheme.colors.onPrimary,
                            modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                        )
                    }
                    if (newNotificationsCount > 0) {
                        Box(
                            modifier = Modifier
                                .padding(SwapAppTheme.dimensions.smallSidePadding)
                                .clip(CircleShape)
                                .background(Color.Red)
                                .size(15.dp)
                                .align(Alignment.BottomStart),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = newNotificationsCount.toString(),
                                style = SwapAppTheme.typography.smallText,
                                modifier = Modifier.wrapContentSize()
                            )
                        }
                    }
                }

                IconButton(onClick = onSettingClick) {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.onPrimary,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
            }
        }
    )
}

@Composable
fun Profile(
    navigateToProfileDetail: (String) -> Unit,
    navigateToItemDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = SwapAppTheme.dimensions.bottomScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        ProfileInfo(koinViewModel(), navigateToProfileDetail)
        ItemsView(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            navigateToItemDetail
        )
    }
}
