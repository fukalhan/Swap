package cz.cvut.fukalhan.swap.itemlist.system.itemdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.Success

@Composable
fun ItemDetail(itemDetailState: Success) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        ImageRow(images = itemDetailState.images)
        Surface(
            elevation = SwapAppTheme.dimensions.elevation,
            shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
            color = SwapAppTheme.colors.backgroundSecondary,
            modifier = Modifier
                .weight(1f)
                .zIndex(0f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SwapAppTheme.dimensions.sidePadding)
            ) {
                TextView(itemDetailState.name, SwapAppTheme.typography.titlePrimary)
                TextView(stringResource(itemDetailState.category.labelId), SwapAppTheme.typography.titleSecondary)
                Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))
                TextView(itemDetailState.description, SwapAppTheme.typography.body)
            }
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
