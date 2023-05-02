package cz.cvut.fukalhan.swap.additem.system

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ButtonRow
import cz.cvut.fukalhan.design.system.components.CategoryListHeader
import cz.cvut.fukalhan.design.system.components.CollapsingList
import cz.cvut.fukalhan.design.system.components.DescriptionView
import cz.cvut.fukalhan.design.system.components.InputFieldView
import cz.cvut.fukalhan.design.system.components.RegularTextFieldView
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.components.screenstate.SuccessSnackMessage
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.additem.R
import cz.cvut.fukalhan.swap.additem.presentation.AddItemState
import cz.cvut.fukalhan.swap.additem.presentation.AddItemViewModel
import cz.cvut.fukalhan.swap.additem.presentation.Failure
import cz.cvut.fukalhan.swap.additem.presentation.Loading
import cz.cvut.fukalhan.swap.additem.presentation.Success
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.categories

const val DESCRIPTION_CHAR_LIMIT = 150

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit,
) {
    val addItemState by viewModel.addItemState.collectAsState()

    TopBar(onScreenInit)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(addItemState, navigateBack, viewModel)
        ItemData(viewModel, navigateBack)
    }
}

@Composable
fun TopBar(onScreenInit: (ScreenState) -> Unit) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.addItem),
                style = SwapAppTheme.typography.screenTitle,
                color = SwapAppTheme.colors.buttonText,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )
        }
    )
}

@Composable
fun ResolveState(
    state: AddItemState,
    navigateBack: () -> Unit,
    viewModel: AddItemViewModel,
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> {
            viewModel.setStateToInit()
            SuccessSnackMessage(state.message)
            navigateBack()
        }
        is Failure -> FailSnackMessage(state.message)
        else -> {}
    }
}

@Composable
fun ItemData(
    viewModel: AddItemViewModel,
    navigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var selectedImagesUri by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.DEFAULT) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PictureSelectionView(selectedImagesUri) {
                selectedImagesUri = it
            }
            Divider(
                color = SwapAppTheme.colors.component,
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
                        name
                    ) {
                        name = it
                    }
                }

                InputFieldView(R.string.description) {
                    DescriptionView(
                        R.string.descriptionPlaceholder,
                        DESCRIPTION_CHAR_LIMIT,
                        description
                    ) {
                        description = it
                    }
                }

                CategoryListHeader(
                    label = stringResource(category.labelId),
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )

                if (expanded) {
                    CollapsingList(
                        items = categories,
                        selectedCategory = category,
                        onItemClick = {
                            category = it
                            expanded = false
                        },
                        itemLabel = {
                            Text(
                                text = stringResource(it.labelId),
                                style = SwapAppTheme.typography.titleSecondary,
                                color = SwapAppTheme.colors.textPrimary,
                            )
                        }
                    )
                }
            }
        }

        ButtonRow(navigateBack) {
            val user = Firebase.auth.currentUser
            user?.let {
                if (name.isBlank() || description.isBlank() || category == Category.DEFAULT) {
                    Toast.makeText(context, context.getText(R.string.allFieldsMustBeFilled), Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.saveItem(
                        it.uid,
                        name,
                        description,
                        selectedImagesUri,
                        category
                    )
                }
            }
        }
    }
}
