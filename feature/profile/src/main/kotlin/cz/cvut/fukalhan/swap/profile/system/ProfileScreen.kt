package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.presentation.ProfileViewModel
import cz.cvut.fukalhan.swap.profile.presentation.useritems.UserItemsViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    userItemsViewModel: UserItemsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SwapAppTheme.dimensions.smallSidePadding,
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
