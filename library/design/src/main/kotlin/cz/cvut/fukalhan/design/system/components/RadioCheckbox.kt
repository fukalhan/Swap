package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.model.RadioCheckboxVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

/**
 * Component for circular checkbox, the selected icon is variable.
 *
 * @param modifier modifier to be applied to the outer layout
 * @param model [RadioCheckboxVo] view object for the component
 */
@Composable
fun RadioCheckbox(
    modifier: Modifier = Modifier,
    model: RadioCheckboxVo
) {
    if (model.isSelected) {
        Icon(
            modifier = modifier
                .size(24.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.Green
                    ),
                    shape = CircleShape
                ),
            painter = painterResource(id = model.selectedIcon),
            tint = Color.Unspecified,
            contentDescription = null,
        )
    } else {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = SwapAppTheme.colors.onSurface
                    ),
                    shape = CircleShape
                )
                .size(24.dp)
        )
    }
}

@Composable
@Preview
fun RadioCheckboxPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        RadioCheckbox(
            model = RadioCheckboxVo(
                isSelected = true,
                selectedIcon = R.drawable.ic_circle_checkbox,
                selectedTint = Color.Green
            )
        )

        RadioCheckbox(
            model = RadioCheckboxVo(
                isSelected = false,
                selectedIcon = R.drawable.ic_circle_checkbox,
                selectedTint = Color.Green
            )
        )
    }
}