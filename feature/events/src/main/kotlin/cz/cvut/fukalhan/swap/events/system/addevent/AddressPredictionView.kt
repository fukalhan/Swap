package cz.cvut.fukalhan.swap.events.system.addevent

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.RegularTextFieldView
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.prediction.PredictionListState
import cz.cvut.fukalhan.swap.events.presentation.prediction.PredictionListViewModel
import cz.cvut.fukalhan.swap.events.presentation.prediction.PredictionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddressPredictionView(
    viewModel: PredictionListViewModel,
    onPickAddressClick: (PredictionState) -> Unit
) {
    val predictionListState by viewModel.predictionListState.collectAsState()
    var address by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var listVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top
    ) {
        RegularTextFieldView(
            label = R.string.insertAddress,
            value = address,
        ) {
            address = it
            scope.launch {
                delay(500)
            }
            listVisible = true
            viewModel.getPredictions(address)
        }

        if (listVisible) {
            Box(
                modifier = Modifier
                    .padding(SwapAppTheme.dimensions.smallSidePadding)
                    .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
                    .border(
                        width = SwapAppTheme.dimensions.borderWidth,
                        color = SwapAppTheme.colors.component,
                        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
                    )
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                ResolveState(predictionListState) {
                    onPickAddressClick(it)
                }
            }
        }
    }
}

@Composable
fun ResolveState(
    state: PredictionListState,
    onAddressClick: (PredictionState) -> Unit
) {
    when (state) {
        is PredictionListState.Loading -> LoadingView()
        is PredictionListState.Empty -> EmptyView(state.message)
        is PredictionListState.Failure -> FailureView(state.message)
        is PredictionListState.Success -> PredictionList(state.predictions, onAddressClick)
        else -> Unit
    }
}

@Composable
fun PredictionList(
    predictions: List<PredictionState>,
    onAddressClick: (PredictionState) -> Unit
) {
    LazyColumn {
        items(predictions) {
            PredictionCard(it, onAddressClick)
            Divider(color = SwapAppTheme.colors.component, thickness = SwapAppTheme.dimensions.borderWidth)
        }
    }
}

@Composable
fun PredictionCard(
    prediction: PredictionState,
    onAddressClick: (PredictionState) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onAddressClick(prediction) }
            .padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        Text(
            text = prediction.description,
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
