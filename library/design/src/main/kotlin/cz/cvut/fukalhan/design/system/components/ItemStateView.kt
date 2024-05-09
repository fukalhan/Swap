package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.theme.semiTransparentBlack

/**
 * Item state view displaying if the item is reserved or exchanged
 *
 * @param modifier modifier of the outer layout
 * @param label the text displayed in the state row
 */
@Composable
fun ItemStateView(
    modifier: Modifier = Modifier,
    label: Int
) {
    Row(
        modifier = modifier
            .background(semiTransparentBlack)
            .fillMaxWidth()
            .padding(end = 15.dp)
            .padding(vertical = 5.dp)
            .zIndex(1f),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = stringResource(label),
            style = SwapAppTheme.typography.button
        )
    }
}

@Composable
@Preview
internal fun ItemStateViewPreview() {
    ItemStateView(
        label = R.string.category_title
    )
}
