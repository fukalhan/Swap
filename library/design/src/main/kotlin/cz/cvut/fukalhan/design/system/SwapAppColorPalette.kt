package cz.cvut.fukalhan.design.system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SwapAppColorPalette(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val backgroundSecondary: Color,
    val componentBackground: Color,
    val component: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val buttonText: Color
)

val swapAppColorPalette = SwapAppColorPalette(
    primary = darkPeach,
    secondary = peach,
    background = white,
    backgroundSecondary = white,
    componentBackground = lightGrey,
    component = grey,
    textPrimary = darkGrey,
    textSecondary = grey,
    buttonPrimary = darkPeach,
    buttonSecondary = grey,
    buttonText = white
)
