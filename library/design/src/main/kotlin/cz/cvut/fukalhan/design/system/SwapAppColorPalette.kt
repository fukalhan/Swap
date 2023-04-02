package cz.cvut.fukalhan.design.system

import androidx.compose.ui.graphics.Color

data class SwapAppColorPalette(
    val background: Color,
    val topBar: Color,
    val topBarText: Color,
    val topBarIcons: Color,
    val bottomBar: Color,
    val bottomBarIcons: Color,
    val button: Color,
    val secondaryButton: Color,
    val tertiaryButton: Color
)

val swapAppColorPalette = SwapAppColorPalette(
    background = beige,
    topBar = peach,
    topBarText = white,
    topBarIcons = white,
    bottomBar = white,
    bottomBarIcons = darkPeach,
    button = darkPeach,
    secondaryButton = darkGrey,
    tertiaryButton = grey
)