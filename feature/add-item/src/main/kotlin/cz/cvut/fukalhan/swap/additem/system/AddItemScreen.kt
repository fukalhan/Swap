package cz.cvut.fukalhan.swap.additem.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ButtonRow
import cz.cvut.fukalhan.design.system.components.CategoryListHeader
import cz.cvut.fukalhan.design.system.components.DescriptionView
import cz.cvut.fukalhan.design.system.components.InputFieldView
import cz.cvut.fukalhan.design.system.components.ListBottomSheet
import cz.cvut.fukalhan.design.system.components.RegularTextFieldView
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.components.screenstate.SuccessSnackMessage
import cz.cvut.fukalhan.design.system.model.ListBottomSheetVo
import cz.cvut.fukalhan.design.theme.semiTransparentBlack
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.model.RadioCheckboxRowVo
import cz.cvut.fukalhan.swap.additem.model.AddItemScreenEvent
import cz.cvut.fukalhan.swap.additem.presentation.AddItemState
import cz.cvut.fukalhan.swap.additem.presentation.AddItemViewModel
import cz.cvut.fukalhan.swap.additem.presentation.Failure
import cz.cvut.fukalhan.swap.additem.presentation.Loading
import cz.cvut.fukalhan.swap.additem.presentation.Success
import cz.cvut.fukalhan.swap.itemdata.model.categories

const val DESCRIPTION_CHAR_LIMIT = 150

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel,
    navigateBack: () -> Unit,
) {
    val addItemState by viewModel.addItemState.collectAsState()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(addItemState, navigateBack) {
            viewModel.setStateToInit()
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImagePicker(
                    selectedImages = viewState.selectedImages,
                    maxImages = viewState.imagesLimit,
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
                        .padding(SwapAppTheme.dimensions.smallSidePadding)
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    InputFieldView(R.string.name) {
                        RegularTextFieldView(
                            R.string.namePlaceholder,
                            viewState.name
                        ) {
                            viewModel.onEvent(AddItemScreenEvent.ItemNameUpdate(it))
                        }
                    }

                    InputFieldView(R.string.description) {
                        DescriptionView(
                            R.string.descriptionPlaceholder,
                            DESCRIPTION_CHAR_LIMIT,
                            viewState.description
                        ) {
                            viewModel.onEvent(AddItemScreenEvent.ItemDescriptionUpdate(it))
                        }
                    }

                    CategoryListHeader(
                        label = stringResource(id = viewState.category?.labelId ?: R.string.category_title),
                        expanded = showBottomSheet,
                        onClick = { showBottomSheet = !showBottomSheet }
                    )

                    if (showBottomSheet) {
                        ListBottomSheet(
                            model = ListBottomSheetVo(
                                title = StringModel.Resource(id = R.string.category_title),
                                items = categories.map { category ->
                                    RadioCheckboxRowVo(
                                        id = category.id,
                                        title = StringModel.Resource(id = category.labelId),
                                        isSelected = viewState.category?.id == category.id
                                    )
                                }
                            ),
                            onCloseClick = {
                                showBottomSheet = false
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

            ButtonRow(navigateBack) {
                viewModel.onEvent(AddItemScreenEvent.OnSaveClick)
            }
        }
    }
}

@Composable
fun ResolveState(
    state: AddItemState,
    navigateBack: () -> Unit,
    setStateToInit: () -> Unit,
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> {
            setStateToInit()
            SuccessSnackMessage(state.message)
            navigateBack()
        }
        is Failure -> FailSnackMessage(state.message)
        else -> {}
    }
}