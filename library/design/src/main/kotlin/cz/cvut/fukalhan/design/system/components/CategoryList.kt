package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun CategoryListHeader(
    label: String,
    expanded: Boolean,
    onClick: () -> Unit,
    padding: Dp = SwapAppTheme.dimensions.smallSidePadding
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        border = BorderStroke(SwapAppTheme.dimensions.borderWidth, SwapAppTheme.colors.component),
        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.backgroundSecondary),
    ) {
        Text(
            text = label,
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = if (expanded) {
                painterResource(R.drawable.arrow_drop_up)
            } else {
                painterResource(
                    R.drawable.arrow_drop_down
                )
            },
            contentDescription = null,
            tint = SwapAppTheme.colors.component
        )
    }
}

@Composable
fun <C> CollapsingList(
    items: List<C>,
    selectedCategory: C,
    onItemClick: (C) -> Unit,
    itemLabel: @Composable (C) -> Unit,
    padding: Dp = SwapAppTheme.dimensions.smallSidePadding,
) {
    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .border(
                width = SwapAppTheme.dimensions.borderWidth,
                color = SwapAppTheme.colors.component,
                shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
            )
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
            .fillMaxWidth()
            .heightIn(max = 200.dp),
        contentPadding = PaddingValues(vertical = SwapAppTheme.dimensions.sidePadding)
    ) {
        items(items) { item ->
            ListItem(item, selectedCategory, onItemClick, itemLabel)
        }
    }
}

@Composable
fun <C> ListItem(
    item: C,
    selectedCategory: C,
    onItemClick: (C) -> Unit,
    label: @Composable (C) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.smallRoundCorners))
            .clickable { onItemClick(item) }
            .background(
                color = if (item == selectedCategory) {
                    SwapAppTheme.colors.componentBackground
                } else {
                    SwapAppTheme.colors.backgroundSecondary
                }
            )
            .fillMaxWidth()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        label.invoke(item)
    }
}
