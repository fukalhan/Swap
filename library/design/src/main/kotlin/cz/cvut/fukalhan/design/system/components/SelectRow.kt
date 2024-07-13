package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.system.model.SelectRowVo
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.theme.SwapAppTheme

/**
 * Component for selectable clickable row
 *
 * @param model the view object with data - label, icons, onClick callback
 * @param modifier the modifier of the layout
 */
@Composable
fun SelectRow(
    model: SelectRowVo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = model.enabled,
                onClick = model.onClick
            )
            .alpha(
                if (model.enabled) 1f else 0.75f
            ),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = SwapAppTheme.colors.background,
            contentColor = SwapAppTheme.colors.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            model.startIcon?.let { icon ->
                Icon(model = icon)
            }

            Text(
                modifier = Modifier.weight(1f),
                text = model.label.getString(),
                style = SwapAppTheme.typography.labelText,
                color = SwapAppTheme.colors.onSurface
            )

            model.endIconVo?.let { icon ->
                Icon(model = icon)
            }
        }
    }
}

@Preview
@Composable
internal fun SelectRowPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SelectRow(
            model = SelectRowVo(
                label = StringModel.String("Select row"),
                onClick = {},
                startIcon = IconVo(
                    res = R.drawable.ic_add
                ),
                endIconVo = IconVo(
                    res = R.drawable.ic_arrow_down,
                )
            )
        )

        SelectRow(
            model = SelectRowVo(
                label = StringModel.String("Disabled select row"),
                onClick = {},
                startIcon = IconVo(
                    res = R.drawable.ic_add
                ),
                endIconVo = IconVo(
                    res = R.drawable.ic_arrow_down,
                ),
                enabled = false
            )
        )
    }
}