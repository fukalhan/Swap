package cz.cvut.fukalhan.swap.additem.system

import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.R

const val DESCRIPTION_CHAR_LIMIT = 150

@Composable
fun AddItemScreen() {
    val scrollState = rememberScrollState()

    var selectedImagesUri by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(SwapAppTheme.dimensions.sidePadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PictureSelectionView(selectedImagesUri) {
            selectedImagesUri = it
        }

        ItemInfoView()
    }
}

@Composable
fun ItemInfoView() {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
                RegularTextFieldView(value = name) {
                    name = it
                }
            }

            InputFieldView(R.string.description) {
                DescriptionView(description) {
                    description = it
                }
            }

            CategoryList()
            ButtonRow({}, {})
        }
    }
}
