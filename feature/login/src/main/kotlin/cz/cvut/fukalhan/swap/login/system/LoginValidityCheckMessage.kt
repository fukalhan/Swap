package cz.cvut.fukalhan.swap.login.system

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
