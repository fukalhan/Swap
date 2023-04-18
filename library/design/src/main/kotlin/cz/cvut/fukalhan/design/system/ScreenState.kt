package cz.cvut.fukalhan.design.system

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

class ScreenState(
    val topBarContent: (@Composable RowScope.() -> Unit)? = null
)
