package cz.cvut.fukalhan.swap.itemlist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.BasicHeader
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.Footer
import cz.cvut.fukalhan.design.system.components.ListBottomSheet
import cz.cvut.fukalhan.design.system.components.SelectRow
import cz.cvut.fukalhan.design.system.components.TextInput
import cz.cvut.fukalhan.design.system.model.BasicHeaderVo
import cz.cvut.fukalhan.design.system.model.ButtonVo
import cz.cvut.fukalhan.design.system.model.FooterVo
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.system.model.ListBottomSheetVo
import cz.cvut.fukalhan.design.system.model.RadioCheckboxRowVo
import cz.cvut.fukalhan.design.system.model.SelectRowVo
import cz.cvut.fukalhan.design.system.model.TextInputVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.swap.itemdata.model.Sorting
import cz.cvut.fukalhan.swap.itemdata.model.categories
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.swap.itemlist.model.ItemsSearchScreenData
import cz.cvut.fukalhan.swap.itemlist.model.ItemsSearchScreenEvent

@Composable
fun ItemSearchScreen(
    viewModel: ComposeViewModel<ItemsSearchScreenData, ItemsSearchScreenEvent>,
) {
    val viewState by viewModel.viewState.collectAsState()
    val showBottomSheet = remember { derivedStateOf { viewState.data.showCategoryBottomSheet } }

    ScreenContentWrapper(
        state = viewState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                BasicHeader(
                    model = BasicHeaderVo(
                        onBackClick = {
                            viewModel.onEvent(ItemsSearchScreenEvent.OnBackClick)
                        }
                    )
                )
            },
            bottomBar = {
                Footer(
                    model = FooterVo(
                        primaryButton = ButtonVo.Basic(
                            label = StringModel.Resource(R.string.search),
                            onClick = {
                                viewModel.onEvent(ItemsSearchScreenEvent.OnSearchClick)
                            }
                        ),
                        secondaryButton = ButtonVo.Basic(
                            label = StringModel.Resource(R.string.cancel),
                            onClick = {
                                viewModel.onEvent(ItemsSearchScreenEvent.OnCancelSearchClick)
                            }
                        )
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextInput(
                    model = TextInputVo(
                        value = viewState.data.searchInput,
                        onValueChange = {
                            viewModel.onEvent(ItemsSearchScreenEvent.OnSearchQueryChange(it))
                        },
                        endIcon = IconVo(
                            res = R.drawable.search
                        )
                    )
                )

                SortBar(
                    sorting = viewState.data.sorting,
                    onSortClick = {
                        viewModel.onEvent(ItemsSearchScreenEvent.OnSortingChange(it))
                    }
                )

                SelectRow(
                    model = SelectRowVo(
                        label = StringModel.Resource(
                            id = viewState.data.category?.labelId
                                ?: R.string.category_title
                        ),
                        onClick = {
                            viewModel.onEvent(
                                ItemsSearchScreenEvent.ChangeCategoryBottomSheetVisibility(
                                    visible = true
                                )
                            )
                        },
                        endIconVo = IconVo(
                            res = R.drawable.ic_arrow_down
                        )
                    )
                )

                if (showBottomSheet.value) {
                    ListBottomSheet(
                        model = ListBottomSheetVo(
                            title = StringModel.Resource(id = cz.cvut.fukalhan.design.R.string.category_title),
                            items = categories.map { category ->
                                RadioCheckboxRowVo(
                                    id = category.id,
                                    title = StringModel.Resource(id = category.labelId),
                                    isSelected = viewState.data.category?.id == category.id
                                )
                            }
                        ),
                        onCloseClick = {
                            viewModel.onEvent(
                                ItemsSearchScreenEvent.ChangeCategoryBottomSheetVisibility(
                                    visible = false
                                )
                            )
                        },
                        onItemClick = {
                            viewModel.onEvent(
                                ItemsSearchScreenEvent.OnCategoryChange(
                                    categories.find { category ->
                                        category.id == it.id
                                    }
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SortBar(
    sorting: Sorting,
    onSortClick: (Sorting) -> Unit
) {
    Text(
        text = stringResource(R.string.sorting),
        style = SwapAppTheme.typography.titleSecondary,
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
            Sorting.RELEVANCE -> SwapAppTheme.colors.surface to SwapAppTheme.colors.background
            Sorting.NEWEST -> SwapAppTheme.colors.background to SwapAppTheme.colors.surface
            else -> SwapAppTheme.colors.background to SwapAppTheme.colors.background
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
private fun SortButton(
    label: Int,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        border = BorderStroke(SwapAppTheme.dimensions.borderWidth, SwapAppTheme.colors.onBackground),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(
            text = stringResource(label),
            style = SwapAppTheme.typography.titleSecondary,
        )
    }
}

@Preview
@Composable
internal fun ItemSearchScreenPreview() {
    ItemSearchScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = ItemsSearchScreenData()
            )
        )
    )
}