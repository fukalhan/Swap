package cz.cvut.fukalhan.design.system.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun FailureView(message: Int) {
    Text(
        text = stringResource(message),
        style = SwapAppTheme.typography.titleSecondary,
        color = SwapAppTheme.colors.textPrimary
    )
}
