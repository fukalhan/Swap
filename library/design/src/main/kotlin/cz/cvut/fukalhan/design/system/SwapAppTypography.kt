package cz.cvut.fukalhan.design.system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class SwapAppTypography(
    val topBarTitle: TextStyle,
    val itemCardDescription: TextStyle
)