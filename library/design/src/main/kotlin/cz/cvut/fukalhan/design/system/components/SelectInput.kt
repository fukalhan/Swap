package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.system.model.SelectInputVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

/**
 * Clickable component to display a value chosen from a set of options
 *
 * @param model view object model
 * @param onClick on component click callback
 * @param modifier layout modifier
 */
@Composable
fun SelectInput(
    model: SelectInputVo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        model.label?.let {
            Text(
                modifier = Modifier.padding(
                    start = 12.dp,
                    bottom = 4.dp
                ),
                text = it.getString(),
                style = SwapAppTheme.typography.body,
                color = SwapAppTheme.colors.onBackground
            )
        }

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = model.enabled,
                    onClick = onClick
                ),
            colors = CardDefaults.cardColors(
                containerColor = SwapAppTheme.colors.secondaryVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Row (
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = model.value.ifBlank {
                        model.placeholder.getString()
                    },
                    style = if (model.value.isBlank()) {
                        SwapAppTheme.typography.body
                    } else {
                        SwapAppTheme.typography.labelText
                    },
                    color = SwapAppTheme.colors.onBackground
                )

                model.endIcon?.let {
                    Icon(
                        model = it.copy(
                            tint = SwapAppTheme.colors.onBackground
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
internal fun SelectInputPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SelectInput(
            model = SelectInputVo(
                label = StringModel.String("Label"),
                value = "Value",
                endIcon = IconVo(res = R.drawable.ic_message),
                placeholder = StringModel.String("Placeholder")
            ),
            onClick = {}
        )

        SelectInput(
            model = SelectInputVo(
                label = StringModel.String("Label"),
                value = "",
                placeholder = StringModel.String("Placeholder"),
                endIcon = IconVo(res = R.drawable.ic_message)
            ),
            onClick = {}
        )
    }

}