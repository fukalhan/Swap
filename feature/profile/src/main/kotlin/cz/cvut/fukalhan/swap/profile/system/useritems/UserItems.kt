package cz.cvut.fukalhan.swap.profile.system.useritems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun UserItems() {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
            .background(SwapAppTheme.colors.componentBackground)
            .fillMaxWidth(),
        columns = GridCells.Fixed(2)
    ) {
        items(10) {
            ItemCard()
        }
    }
}
