package cz.cvut.fukalhan.design.system

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SwapAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = swapAppColorPalette,
        content = content
    )
}
object SwapAppTheme {
    val colors = swapAppColorPalette
    val typography = swapAppTypography
    val dimensions = swapAppDimensions
}
