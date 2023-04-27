package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.system.items.ItemsView
import cz.cvut.fukalhan.swap.profile.system.profileinfo.ProfileInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onScreenInit: (ScreenState) -> Unit,
    navigateToNotifications: () -> Unit,
    onSettingsClick: () -> Unit,
    navigateToProfileDetail: (String) -> Unit
) {
    TopBar(onScreenInit, navigateToNotifications, onSettingsClick)
    Profile(navController, navigateToProfileDetail)
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    navigateToNotifications: () -> Unit,
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

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigateToNotifications) {
                    Icon(
                        painter = painterResource(R.drawable.notifications),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }

                IconButton(onClick = onSettingClick) {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
            }
        }
    )
}

@Composable
fun Profile(
    navController: NavHostController,
    navigateToProfileDetail: (String) -> Unit
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
            navController
        )
    }
}
