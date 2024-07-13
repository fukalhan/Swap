package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
 * Component for secondary button
 *
 * @param model [ButtonVo.Basic] view object
 * @param modifier layout modifier
 */
@Composable
fun SecondaryButton(
    model: ButtonVo.Basic,
    modifier: Modifier = Modifier
) {
    BasicButton(
        model = model,
        colors = ButtonDefaults.buttonColors(
            containerColor = SwapAppTheme.colors.secondaryVariant,
            contentColor = SwapAppTheme.colors.onPrimary,
            disabledContainerColor = SwapAppTheme.colors.secondaryVariant.copy(
                alpha = 0.75f
            ),
            disabledContentColor = SwapAppTheme.colors.onPrimary.copy(
                alpha = 0.75f
            )
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SecondaryButton(
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

        SecondaryButton(
            model = ButtonVo.Basic(
                enabled = false,
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
}