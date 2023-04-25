package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun ItemCard(
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            content.invoke(this)
        }
    }
}
