package cz.cvut.fukalhan.design.system.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * View object for icons
 *
 * @property res icon resource
 * @property size icon size, default 24.dp
 * @property tint icon tint, default color unspecified
 * @property contentDescription optional content description
 */
data class IconVo(
    @DrawableRes
    val res: Int,
    val size: Dp = 24.dp,
    val tint: Color = Color.Unspecified,
    val contentDescription: StringModel? = null
)