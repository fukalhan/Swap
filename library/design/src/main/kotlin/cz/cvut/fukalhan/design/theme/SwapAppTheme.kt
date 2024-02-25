package cz.cvut.fukalhan.design.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SwapAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colorPalette,
        content = content
    )
}
object SwapAppTheme {
    val colors = colorPalette
    val typography = cz.cvut.fukalhan.design.theme.typography
    val dimensions = cz.cvut.fukalhan.design.theme.dimensions
}
