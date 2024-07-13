package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.system.model.IconButtonVo
import androidx.compose.material.IconButton

/**
 * Component simplifying usage of [IconButton] with [IconButtonVo] view object
 *
 * @param model [IconButtonVo] view object
 * @param modifier layout modifier
 */
@Composable
fun IconButton(
    model: IconButtonVo,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = model.onClick,
        modifier = modifier.size(model.size)
    ) {
        Icon(
            model = model.iconVo
        )
    }
}