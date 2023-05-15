package cz.cvut.fukalhan.swap.events.system.addevent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.prediction.PredictionState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddressInputView(
    onAddressPicked: (PredictionState) -> Unit
) {
    val emptyAddress = stringResource(R.string.emptyAddress)

    var address by remember { mutableStateOf(emptyAddress) }
    var isEditMode by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.address),
                style = SwapAppTheme.typography.titleSecondary,
            )
            if (isEditMode) {
                AddressPredictionView(koinViewModel()) {
                    address = it.description
                    isEditMode = !isEditMode
                    onAddressPicked(it)
                }
            } else {
                Text(
                    text = address,
                    style = SwapAppTheme.typography.body,
                )
            }
        }

        if (!isEditMode) {
            Surface(
                shape = CircleShape,
                elevation = SwapAppTheme.dimensions.elevation
            ) {
                IconButton(
                    onClick = { isEditMode = !isEditMode },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SwapAppTheme.colors.primary)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_location),
                        null,
                        tint = SwapAppTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
