package cz.cvut.fukalhan.swap.profiledetail.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profiledetail.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TabsView(
    userId: String,
    modifier: Modifier,
    navigateToProfileDetail: (String) -> Unit,
    navigateToItemDetail: (String) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    val isCurrentUserProfile = Firebase.auth.currentUser?.let { it.uid == userId } ?: run { false }
    val tabs = if (isCurrentUserProfile) {
        listOf(stringResource(R.string.reviews))
    } else {
        listOf(stringResource(R.string.reviews), stringResource(R.string.items))
    }

    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier.background(SwapAppTheme.colors.background)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title,
                                style = SwapAppTheme.typography.button,
                                color = SwapAppTheme.colors.onBackground
                            )
                        },
                        modifier = Modifier
                            .background(
                                if (tabIndex == index) {
                                    SwapAppTheme.colors.background
                                } else {
                                    SwapAppTheme.colors.secondaryVariant
                                }
                            ),
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }

            when (tabIndex) {
                0 -> Reviews(userId, koinViewModel(), isCurrentUserProfile, navigateToProfileDetail)
                1 -> ItemListView(userId, koinViewModel(), navigateToItemDetail)
            }
        }
    }
}
