package cz.cvut.fukalhan.design.system.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * Simple information message bar to be displayed on the bottom of the screen
 */
@Composable
fun SnackbarMessage(message: StringModel) {
    val context = LocalContext.current
    Toast.makeText(context, message.getString(), Toast.LENGTH_SHORT).show()
}