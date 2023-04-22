package cz.cvut.fukalhan.swap.additem.system.helperviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.R

@Composable
fun ButtonRow(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ButtonView(
            R.string.cancel,
            ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonSecondary),
            onCancelClick
        )

        ButtonView(
            R.string.save,
            ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary),
            onSaveClick
        )
    }
}

@Composable
fun ButtonView(
    labelRes: Int,
    buttonColors: ButtonColors,
    onClick: () -> Unit
) {
    Button(
        colors = buttonColors,
        onClick = onClick
    ) {
        Text(
            text = stringResource(labelRes),
            style = SwapAppTheme.typography.button,
            color = SwapAppTheme.colors.buttonText
        )
    }
}
