package cz.cvut.fukalhan.design.system.components.screenstate

import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun FailureView(message: Int) {
    Text(
        text = stringResource(message),
        style = SwapAppTheme.typography.titleSecondary,
    )
}

@Composable
fun FailSnackMessage(message: Int) {
    val context = LocalContext.current
    Toast.makeText(context, stringResource(message), Toast.LENGTH_SHORT).show()
}
