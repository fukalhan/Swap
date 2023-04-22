package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.items.LikedItemListViewModel
import cz.cvut.fukalhan.swap.profile.presentation.items.UserItemsViewModel
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.ProfileInfoViewModel
import cz.cvut.fukalhan.swap.profile.system.items.ItemsView
import cz.cvut.fukalhan.swap.profile.system.profileinfo.ProfileInfo
import io.getstream.chat.android.client.ChatClient

@Composable
fun ProfileScreen(
    chatClient: ChatClient,
    profileInfoViewModel: ProfileInfoViewModel,
    userItemsViewModel: UserItemsViewModel,
    likedItemListViewModel: LikedItemListViewModel,
    onScreenInit: (ScreenState) -> Unit,
    onSettingsClick: () -> Unit
) {
    TopBar(onScreenInit, onSettingsClick)
    Profile(profileInfoViewModel, userItemsViewModel, likedItemListViewModel, chatClient)
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    onSettingClick: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.profile),
                style = SwapAppTheme.typography.screenTitle,
                color = SwapAppTheme.colors.buttonText,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )
            IconButton(onClick = onSettingClick) {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = null,
                    tint = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                )
            }
        }
    )
}

@Composable
fun Profile(
    profileInfoViewModel: ProfileInfoViewModel,
    userItemsViewModel: UserItemsViewModel,
    likedItemListViewModel: LikedItemListViewModel,
    chatClient: ChatClient
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = SwapAppTheme.dimensions.bottomScreenPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        ProfileInfo(profileInfoViewModel, chatClient)
        ItemsView(
            userItemsViewModel,
            likedItemListViewModel,
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}
