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
import cz.cvut.fukalhan.design.system.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.ProfileViewModel
import cz.cvut.fukalhan.swap.profile.presentation.useritems.UserItemsViewModel
import cz.cvut.fukalhan.swap.profile.system.profileinfo.ProfileInfo

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    userItemsViewModel: UserItemsViewModel,
    onScreenInit: (ScreenState) -> Unit,
    onSettingsClick: () -> Unit
) {
    TopBar(onScreenInit, onSettingsClick)
    Profile(profileViewModel, userItemsViewModel)
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
    profileViewModel: ProfileViewModel,
    userItemsViewModel: UserItemsViewModel
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
        ProfileInfo(profileViewModel)
        ItemsView(
            userItemsViewModel,
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}
