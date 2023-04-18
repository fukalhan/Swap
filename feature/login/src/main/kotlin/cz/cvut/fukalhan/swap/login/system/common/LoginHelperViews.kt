package cz.cvut.fukalhan.swap.login.system.common

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun LoginValidityCheckMessage(conditionMet: Boolean, mesResId: Int) {
    if (!conditionMet) {
        Text(
            text = stringResource(mesResId)
        )
    }
}

@Composable
fun ShowLoginStateMessage(context: Context, messageId: Int, showMessage: Boolean, afterShowMessage: () -> Unit) {
    if (showMessage) {
        Toast.makeText(context, stringResource(messageId), Toast.LENGTH_SHORT).show()
        afterShowMessage()
    }
}
