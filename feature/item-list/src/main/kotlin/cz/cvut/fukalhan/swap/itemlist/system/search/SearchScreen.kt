package cz.cvut.fukalhan.swap.itemlist.system.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.CategoryListHeader
import cz.cvut.fukalhan.design.system.components.CollapsingList
import cz.cvut.fukalhan.design.system.components.getTextFieldColors
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.SearchQuery
import cz.cvut.fukalhan.swap.itemdata.model.Sorting
import cz.cvut.fukalhan.swap.itemdata.model.categories
import cz.cvut.fukalhan.swap.itemlist.R

@Composable
fun SearchScreen(
    visible: Boolean,
    closeSearchView: () -> Unit,
    onSearchClick: (SearchQuery) -> Unit,
) {
    AnimatedVisibility(
        modifier = Modifier.zIndex(1f),
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .background(semiTransparentBlack)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedVisibility(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                visible = visible,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                // Workaround to make the search view rounded only on bottom side
                Box(
                    modifier = Modifier
                        .background(SwapAppTheme.colors.backgroundSecondary)
                        .fillMaxWidth()
                        .height(SwapAppTheme.dimensions.mediumSpacer)
                )
                Column(modifier = Modifier.fillMaxSize()) {
                    SearchView(onSearchClick)

                    // Workaround to make just the rest of the view beside the search clickable
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable { closeSearchView() }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchView(
    onSearchClick: (SearchQuery) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
            .background(SwapAppTheme.colors.backgroundSecondary)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var searchQuery by remember { mutableStateOf("") }
        var sorting by remember { mutableStateOf(Sorting.DEFAULT) }
        var category by remember { mutableStateOf(Category.DEFAULT) }
        var expanded by remember { mutableStateOf(false) }

        SearchBar(searchQuery, onSearchQueryChange = { searchQuery = it })

        SortBar(sorting) {
            sorting = it
        }

        CategoryList(
            category,
            expanded,
            onExpandedChange = { expanded = it },
            onCategoryChange = { category = it }
        )

        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.mediumSpacer))
        SearchButton {
            onSearchClick(SearchQuery(searchQuery, sorting, category))
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .padding(top = SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth(),
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        colors = getTextFieldColors(),
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = null,
                tint = SwapAppTheme.colors.buttonText
            )
        }
    )
}

@Composable
fun SortBar(
    sorting: Sorting,
    onSortClick: (Sorting) -> Unit
) {
    Text(
        text = stringResource(R.string.sorting),
        style = SwapAppTheme.typography.titleSecondary,
        color = SwapAppTheme.colors.textPrimary,
        modifier = Modifier
            .padding(
                start = SwapAppTheme.dimensions.sidePadding,
                top = SwapAppTheme.dimensions.sidePadding
            )
            .fillMaxWidth()
    )
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val (color1, color2) = when (sorting) {
            Sorting.RELEVANCE -> SwapAppTheme.colors.componentBackground to SwapAppTheme.colors.backgroundSecondary
            Sorting.NEWEST -> SwapAppTheme.colors.backgroundSecondary to SwapAppTheme.colors.componentBackground
            else -> SwapAppTheme.colors.backgroundSecondary to SwapAppTheme.colors.backgroundSecondary
        }

        SortButton(R.string.accordingToRelevance, color1) {
            onSortClick(Sorting.RELEVANCE)
        }

        SortButton(R.string.fromTheNewest, color2) {
            onSortClick(Sorting.NEWEST)
        }
    }
}

@Composable
fun SortButton(
    label: Int,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        border = BorderStroke(SwapAppTheme.dimensions.borderWidth, SwapAppTheme.colors.component),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(
            text = stringResource(label),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )
    }
}

@Composable
fun CategoryList(
    category: Category,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCategoryChange: (Category) -> Unit
) {
    CategoryListHeader(
        stringResource(category.labelId),
        expanded,
        onClick = {
            onExpandedChange(!expanded)
        },
        padding = SwapAppTheme.dimensions.sidePadding
    )

    if (expanded) {
        CollapsingList(
            categories,
            category,
            onItemClick = {
                onCategoryChange(it)
                onExpandedChange(false)
            },
            itemLabel = {
                Text(
                    text = stringResource(it.labelId),
                    style = SwapAppTheme.typography.titleSecondary,
                    color = SwapAppTheme.colors.textPrimary,
                )
            },
            padding = SwapAppTheme.dimensions.sidePadding
        )
    }
}

@Composable
fun SearchButton(
    onSearchClick: () -> Unit
) {
    Button(
        onClick = onSearchClick,
        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.primary),
        modifier = Modifier.padding(SwapAppTheme.dimensions.sidePadding)
    ) {
        Text(
            text = stringResource(R.string.search),
            style = SwapAppTheme.typography.button,
            color = SwapAppTheme.colors.buttonText
        )
    }
}
