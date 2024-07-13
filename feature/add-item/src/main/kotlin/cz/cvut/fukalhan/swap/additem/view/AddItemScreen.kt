package cz.cvut.fukalhan.swap.additem.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.DescriptionView
import cz.cvut.fukalhan.design.system.components.InputFieldView
import cz.cvut.fukalhan.design.system.components.ListBottomSheet
import cz.cvut.fukalhan.design.system.components.RegularTextFieldView
import cz.cvut.fukalhan.design.system.model.ListBottomSheetVo
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.Footer
import cz.cvut.fukalhan.design.system.components.SelectRow
import cz.cvut.fukalhan.design.system.model.ButtonVo
import cz.cvut.fukalhan.design.system.model.FooterVo
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.system.model.RadioCheckboxRowVo
import cz.cvut.fukalhan.design.system.model.SelectRowVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.swap.additem.model.AddItemScreenData
import cz.cvut.fukalhan.swap.additem.model.AddItemScreenEvent
import cz.cvut.fukalhan.swap.itemdata.model.categories

/**
 * Screen for adding a new item
 *
 * @param viewModel view model for this screen
 * @param navigateBack function to navigate back
 */
@Composable
fun AddItemScreen(
    viewModel: ComposeViewModel<AddItemScreenData, AddItemScreenEvent>,
    navigateBack: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val showBottomSheet = remember { derivedStateOf { viewState.data.showCategoryBottomSheet } }

    ScreenContentWrapper(
        state = viewState,
        onSuccessAction = navigateBack,
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    Footer(
                        model = FooterVo(
                            primaryButton = ButtonVo.Basic(
                                label = StringModel.Resource(id = R.string.save),
                                onClick = {
                                    viewModel.onEvent(AddItemScreenEvent.OnSaveClick)
                                },
                                enabled = viewState.data.isSaveButtonEnabled
                            ),
                            secondaryButton = ButtonVo.Basic(
                                label = StringModel.Resource(id = R.string.cancel),
                                onClick = navigateBack
                            )
                        )
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImagePicker(
                        selectedImages = viewState.data.selectedImages,
                        maxImages = viewState.data.imagesLimit,
                        onSelectImages = {
                            viewModel.onEvent(AddItemScreenEvent.AddItemImages(it))
                        },
                        onRemoveImage = {
                            viewModel.onEvent(AddItemScreenEvent.RemoveItemImage(it))
                        }
                    )

                    Divider(
                        color = SwapAppTheme.colors.onBackground,
                        thickness = SwapAppTheme.dimensions.borderWidth,
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        InputFieldView(R.string.name) {
                            RegularTextFieldView(
                                R.string.namePlaceholder,
                                viewState.data.name
                            ) {
                                viewModel.onEvent(AddItemScreenEvent.ItemNameUpdate(it))
                            }
                        }

                        InputFieldView(R.string.description) {
                            DescriptionView(
                                R.string.descriptionPlaceholder,
                                AddItemScreenData.DESCRIPTION_CHAR_LIMIT,
                                viewState.data.description
                            ) {
                                viewModel.onEvent(AddItemScreenEvent.ItemDescriptionUpdate(it))
                            }
                        }

                        SelectRow(
                            model = SelectRowVo(
                                label = StringModel.Resource(
                                    id = viewState.data.category?.labelId
                                        ?: R.string.category_title
                                ),
                                onClick = {
                                    viewModel.onEvent(
                                        AddItemScreenEvent.ChangeCategoryBottomSheetVisibility(
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
                                    title = StringModel.Resource(id = R.string.category_title),
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
                                        AddItemScreenEvent.ChangeCategoryBottomSheetVisibility(
                                            visible = false
                                        )
                                    )
                                },
                                onItemClick = {
                                    viewModel.onEvent(
                                        AddItemScreenEvent.ItemCategoryUpdate(
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
    )
}

@Composable
@Preview
internal fun AddItemScreenPreview() {
    AddItemScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = AddItemScreenData()
            )
        ),
        navigateBack = {}
    )
}