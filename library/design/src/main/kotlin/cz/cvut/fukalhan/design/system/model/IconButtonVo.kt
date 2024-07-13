package cz.cvut.fukalhan.design.system.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * View object for IconButton to simplify the usage of IconButton with [IconVo]
 *
 * @property iconVo icon view object
 * @property onClick on click callback
 * @property size icon button size
 */
data class IconButtonVo(
    val iconVo: IconVo,
    val onClick: () -> Unit,
    val size: Dp = 44.dp,
)