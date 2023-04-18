package cz.cvut.fukalhan.design.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

class ScreenState(
    val topBarContent: (@Composable RowScope.() -> Unit)? = null
)
