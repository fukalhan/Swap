package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.model.ListBottomSheetVo
import cz.cvut.fukalhan.design.system.model.RadioCheckboxRowVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import kotlinx.coroutines.launch

/**
 * Component for displaying bottom sheet with list
 *
 * @param onCloseClick on close button click callback
 * @param onItemClick on item click callback
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBottomSheet(
    modifier: Modifier = Modifier,
    model: ListBottomSheetVo,
    onCloseClick: () -> Unit,
    onItemClick: (RadioCheckboxRowVo) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onCloseClick() },
        sheetState = sheetState
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = model.title.getString(),
                style = SwapAppTheme.typography.titlePrimary
            )

            IconButton(
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if(!sheetState.isVisible) {
                            onCloseClick()
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(model.items) {
                RadioCheckboxRow(
                    model = it
                ) {
                    onItemClick(it)
                }
            }
        }
    }
}