package cz.cvut.fukalhan.swap.additem.system

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.R
import cz.cvut.fukalhan.swap.additem.presentation.AddItemState
import cz.cvut.fukalhan.swap.additem.presentation.AddItemViewModel
import cz.cvut.fukalhan.swap.additem.presentation.Failure
import cz.cvut.fukalhan.swap.additem.presentation.Loading
import cz.cvut.fukalhan.swap.additem.presentation.Success
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
    val addItemState by viewModel.addItemState.collectAsState()

    TopBar(onScreenInit)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(addItemState)
        OnSuccessState(addItemState) {
            viewModel.setStateToInit()
            navigateBack()
        }
        OnFailState(addItemState)
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
fun LoadingView(saveItemState: AddItemState) {
    if (saveItemState is Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(SwapAppTheme.dimensions.icon),
                color = SwapAppTheme.colors.primary
            )
        }
    }
}

@Composable
fun OnSuccessState(
    addItemState: AddItemState,
    navigateBack: () -> Unit
) {
    if (addItemState is Success) {
        val context = LocalContext.current
        Toast.makeText(context, stringResource(addItemState.message), Toast.LENGTH_SHORT).show()
        navigateBack()
    }
}

@Composable
fun OnFailState(addItemState: AddItemState) {
    if (addItemState is Failure) {
        val context = LocalContext.current
        Toast.makeText(context, stringResource(addItemState.message), Toast.LENGTH_SHORT).show()
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
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(SwapAppTheme.colors.backgroundSecondary)
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
                modifier = Modifier.padding(
                    start = SwapAppTheme.dimensions.sidePadding,
                    end = SwapAppTheme.dimensions.sidePadding,
                )
            )

            Column(
                modifier = Modifier
                    .padding(SwapAppTheme.dimensions.smallSidePadding)
                    .wrapContentHeight()
                    .fillMaxWidth()
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
            }
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
