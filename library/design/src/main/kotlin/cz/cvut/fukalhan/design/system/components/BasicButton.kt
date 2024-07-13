package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.ButtonVo
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

/**
 * Component for basic button layout
 *
 * @param model [ButtonVo.Basic] view object
 * @param colors button colors
 * @param modifier layout modifier
 */
@Composable
fun BasicButton(
    model: ButtonVo.Basic,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = SwapAppTheme.colors.primary,
        contentColor = SwapAppTheme.colors.onPrimary,
        disabledContainerColor = SwapAppTheme.colors.primary.copy(
            alpha = 0.75f
        ),
        disabledContentColor = SwapAppTheme.colors.onPrimary.copy(
            alpha = 0.75f
        )
    ),
    modifier: Modifier = Modifier
) {
    Button(
        onClick = model.onClick,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = model.enabled,
        shape = RoundedCornerShape(12.dp),
        colors = colors,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
    ) {
        model.startIcon?.let { icon ->
            Icon(
                model = icon.copy(
                    size = 20.dp
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Text(
            text = model.label.getString(),
            style = SwapAppTheme.typography.button
        )

        model.endIcon?.let { icon ->
            Icon(
                model = icon.copy(
                    size = 20.dp
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
internal fun BasicButtonPreview() {
    BasicButton(
        model = ButtonVo.Basic(
            label = StringModel.String("Button"),
            onClick = {},
            startIcon = IconVo(
                res = R.drawable.ic_add
            ),
            endIcon = IconVo(
                res = R.drawable.ic_add
            )
        )
    )
}