package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.ButtonVo
import cz.cvut.fukalhan.design.system.model.FooterVo
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

/**
 * Component for displaying bottom footer with buttons and optional additional content
 *
 * @param model view object for the component
 * @param modifier modifier of the outer layout
 *
 */
@Composable
fun Footer(
    model: FooterVo,
    modifier: Modifier = Modifier,
    additionalContent: @Composable (() -> Unit)? = null,
    backgroundColor: Color = SwapAppTheme.colors.background
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        additionalContent?.invoke()

        model.secondaryButton?.let {
            SecondaryButton(model = it)
        }

        PrimaryButton(model = model.primaryButton)
    }
}

@Preview
@Composable
fun FooterPreview() {
    Footer(
        model = FooterVo(
            primaryButton = ButtonVo.Basic(
                label = StringModel.String("Primary Button"),
                onClick = {},
                startIcon = IconVo(
                    res = R.drawable.ic_add
                ),
                endIcon = IconVo(
                    res = R.drawable.ic_add
                )
            ),
            secondaryButton = ButtonVo.Basic(
                label = StringModel.String("Secondary Button"),
                onClick = {},
                startIcon = IconVo(
                    res = R.drawable.ic_add
                ),
                endIcon = IconVo(
                    res = R.drawable.ic_add
                )
            )
        ),
        additionalContent = {
            Text(
                text = "Additional footer content",
                style = SwapAppTheme.typography.body
            )
        }
    )
}

