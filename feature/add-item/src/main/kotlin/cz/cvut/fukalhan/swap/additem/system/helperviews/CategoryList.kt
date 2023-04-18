package cz.cvut.fukalhan.swap.additem.system.helperviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.R
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.categories

@Composable
fun CategoryList(
    selectedCategory: Category,
    onItemClick: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = categories

    Column {
        CategoryListHeader(selectedCategory, expanded) {
            expanded = !expanded
        }

        if (expanded) {
            CollapsingList(categories, selectedCategory) {
                onItemClick(it)
                expanded = false
            }
        }
    }
}

@Composable
fun CategoryListHeader(
    selectedCategory: Category,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .border(
                width = SwapAppTheme.dimensions.borderWidth,
                color = SwapAppTheme.colors.component,
                shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
            )
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(selectedCategory.labelId),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary,
        )
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
fun CollapsingList(
    items: List<Category>,
    selectedCategory: Category,
    onItemClick: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
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
            ListItem(item, selectedCategory, onItemClick)
        }
    }
}

@Composable
fun ListItem(
    item: Category,
    selectedCategory: Category,
    onClick: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.smallRoundCorners))
            .clickable { onClick(item) }
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
        Text(
            text = stringResource(item.labelId),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary,
        )
    }
}
