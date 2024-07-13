package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.R

/**
 * Custom icon component taking [IconVo] as its view object to set the data
 *
 * @param model [IconVo] model
 * @param modifier layout modifier
 */
@Composable
fun Icon(
    model: IconVo,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier
            .size(model.size),
        painter = painterResource(id = model.res),
        tint = model.tint,
        contentDescription = model.contentDescription?.getString()
    )
}

@Preview
@Composable
internal fun IconPreview() {
    Icon(
        model = IconVo(
            res = R.drawable.ic_add
        )
    )
}