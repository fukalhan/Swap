package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.useritems.UserItemsViewModel
import cz.cvut.fukalhan.swap.profile.system.items.LikedItemsList
import cz.cvut.fukalhan.swap.profile.system.items.UserItemList

@Composable
fun ItemsView(
    userItemsViewModel: UserItemsViewModel,
    modifier: Modifier
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(stringResource(R.string.myItems), stringResource(R.string.likedItems))

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title,
                                style = SwapAppTheme.typography.button,
                                color = SwapAppTheme.colors.textPrimary
                            )
                        },
                        modifier = Modifier
                            .background(
                                if (tabIndex == index) {
                                    SwapAppTheme.colors.backgroundSecondary
                                } else {
                                    SwapAppTheme.colors.componentBackground
                                }
                            ),
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }

            when (tabIndex) {
                0 -> UserItemList(userItemsViewModel)
                1 -> LikedItemsList()
            }
        }
    }
}