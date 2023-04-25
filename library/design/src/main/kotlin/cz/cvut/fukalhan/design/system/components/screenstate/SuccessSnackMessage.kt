package cz.cvut.fukalhan.design.system.components.screenstate

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun SuccessSnackMessage(message: Int) {
    val context = LocalContext.current
    Toast.makeText(context, stringResource(message), Toast.LENGTH_SHORT).show()
}
