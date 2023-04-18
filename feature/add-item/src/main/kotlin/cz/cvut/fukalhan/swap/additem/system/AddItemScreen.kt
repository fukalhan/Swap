package cz.cvut.fukalhan.swap.additem.system

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
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
import cz.cvut.fukalhan.design.system.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.R
import cz.cvut.fukalhan.swap.additem.presentation.AddItemState
import cz.cvut.fukalhan.swap.additem.presentation.AddItemViewModel
import cz.cvut.fukalhan.swap.additem.presentation.FailedState
import cz.cvut.fukalhan.swap.additem.presentation.SuccessfulState
import cz.cvut.fukalhan.swap.additem.system.helperviews.ButtonRow
import cz.cvut.fukalhan.swap.additem.system.helperviews.CategoryList
import cz.cvut.fukalhan.swap.additem.system.helperviews.DescriptionView
import cz.cvut.fukalhan.swap.additem.system.helperviews.InputFieldView
import cz.cvut.fukalhan.swap.additem.system.helperviews.PictureSelectionView
import cz.cvut.fukalhan.swap.additem.system.helperviews.RegularTextFieldView
import cz.cvut.fukalhan.swap.itemdata.model.Category

const val DESCRIPTION_CHAR_LIMIT = 150

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit,
) {
    val saveItemState by viewModel.saveItemState.collectAsState()

    TopBar(onScreenInit)

    DisplaySaveItemResponseMessage(viewModel, LocalContext.current, saveItemState, navigateBack)

    ItemData(viewModel, navigateBack)
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
fun DisplaySaveItemResponseMessage(
    viewModel: AddItemViewModel,
    context: Context,
    itemState: AddItemState,
    navigateBack: () -> Unit
) {
    viewModel.neutralizeItemState()
    if (itemState is SuccessfulState || itemState is FailedState) {
        Toast.makeText(context, stringResource(itemState.messageRes), Toast.LENGTH_SHORT).show()
    }

    if (itemState is SuccessfulState) {
        navigateBack()
    }
}

@Composable
fun ItemData(
    viewModel: AddItemViewModel,
    navigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    var selectedImagesUri by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.DEFAULT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(SwapAppTheme.dimensions.smallSidePadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PictureSelectionView(selectedImagesUri) {
            selectedImagesUri = it
        }

        Surface(
            elevation = SwapAppTheme.dimensions.elevation,
            shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
            color = SwapAppTheme.colors.backgroundSecondary,
            modifier = Modifier
                .padding(bottom = SwapAppTheme.dimensions.sidePadding)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SwapAppTheme.dimensions.sidePadding)
            ) {
                InputFieldView(R.string.name) {
                    RegularTextFieldView(name) {
                        name = it
                    }
                }

                InputFieldView(R.string.description) {
                    DescriptionView(description) {
                        description = it
                    }
                }

                CategoryList(category) {
                    category = it
                }

                ButtonRow(navigateBack) {
                    val user = Firebase.auth.currentUser
                    user?.let {
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
}
