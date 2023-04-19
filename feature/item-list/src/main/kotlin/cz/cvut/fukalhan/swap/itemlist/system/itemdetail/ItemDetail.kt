package cz.cvut.fukalhan.swap.itemlist.system.itemdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.Success

@Composable
fun ItemDetail(itemDetailState: Success) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ImageRow(images = itemDetailState.images)
        Column(
            modifier = Modifier
                .background(SwapAppTheme.colors.backgroundSecondary)
                .weight(1f)
                .fillMaxWidth()
                .padding(SwapAppTheme.dimensions.sidePadding)
        ) {
            TextView(itemDetailState.name, SwapAppTheme.typography.titlePrimary)
            TextView(stringResource(itemDetailState.category.labelId), SwapAppTheme.typography.titleSecondary)
            Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))
            TextView(itemDetailState.description, SwapAppTheme.typography.body)
        }
    }
}

@Composable
fun TextView(text: String, style: TextStyle) {
    Text(
        text = text,
        style = style,
        color = SwapAppTheme.colors.textPrimary
    )
}
