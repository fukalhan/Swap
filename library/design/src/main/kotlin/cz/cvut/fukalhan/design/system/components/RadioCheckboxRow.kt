package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.RadioCheckboxRowVo
import cz.cvut.fukalhan.design.system.model.RadioCheckboxVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

/**
 * Component for row with title and circular checkbox
 *
 * @param modifier to be applied to the outer layout
 * @param model view object for the row
 */
@Composable
fun RadioCheckboxRow(
    modifier: Modifier = Modifier,
    model: RadioCheckboxRowVo,
    onClick: (RadioCheckboxRowVo) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp)
            .clickable {
                onClick(model)
            }
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = model.title.getString(),
            style = SwapAppTheme.typography.titleSecondary
        )

        Spacer(modifier = Modifier.width(8.dp))

        RadioCheckbox(
            model = RadioCheckboxVo(
                isSelected = model.isSelected,
                selectedIcon = R.drawable.ic_circle_checkbox,
                selectedTint = Color.Green
            )
        )
    }
}

@Composable
@Preview
fun RadioCheckboxRowPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RadioCheckboxRow(
            model = RadioCheckboxRowVo(
                id = 1,
                title = StringModel.String("Option 1"),
                isSelected = true
            ),
            onClick = {}
        )

        RadioCheckboxRow(
            model = RadioCheckboxRowVo(
                id = 1,
                title = StringModel.String("Option 2"),
                isSelected = false
            ),
            onClick = {}
        )
    }
}