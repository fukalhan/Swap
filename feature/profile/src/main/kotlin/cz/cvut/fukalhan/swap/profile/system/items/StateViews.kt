package cz.cvut.fukalhan.swap.profile.system.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.presentation.items.Failure
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.items.Loading

@Composable
fun LoadingView(itemListState: ItemListState) {
    if (itemListState is Loading) {
        Box(modifier = Modifier.wrapContentSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(SwapAppTheme.dimensions.icon),
                color = SwapAppTheme.colors.primary
            )
        }
    }
}

@Composable
fun FailView(itemListState: ItemListState) {
    if (itemListState is Failure) {
        Text(
            text = stringResource(itemListState.message),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )
    }
}
