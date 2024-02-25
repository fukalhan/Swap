package cz.cvut.fukalhan.swap.profiledetail.system.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.swap.profiledetail.R

@Composable
fun Bio(bio: String) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier
            .padding(
                top = SwapAppTheme.dimensions.smallSidePadding,
                bottom = SwapAppTheme.dimensions.smallSidePadding
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.bio),
                style = SwapAppTheme.typography.titleSecondary,
                modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
            )
            val content = bio.ifEmpty { stringResource(R.string.emptyBio) }
            Text(
                text = content,
                style = SwapAppTheme.typography.body,
                modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
            )
        }
    }
}
