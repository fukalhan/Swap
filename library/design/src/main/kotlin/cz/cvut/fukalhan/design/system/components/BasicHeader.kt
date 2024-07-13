package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.BasicHeaderVo
import cz.cvut.fukalhan.design.system.model.IconButtonVo
import cz.cvut.fukalhan.design.system.model.IconVo

@Composable
fun BasicHeader(
    model: BasicHeaderVo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = SwapAppTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        model.backButton?.let { iconButton ->
            IconButton(
                model = iconButton
            )
        } ?: Spacer(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.weight(4f),
            text = model.title.getString(),
            style = SwapAppTheme.typography.screenTitle,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        model.endIcons?.let { icons ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                icons.forEach { iconButton ->
                    IconButton(
                        model = iconButton.copy(
                            iconVo = iconButton.iconVo.copy(
                                tint = SwapAppTheme.colors.onPrimary
                            )
                        )
                    )
                }
            }
        } ?: Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
internal fun BasicHeaderPreview() {
    BasicHeader(
        model = BasicHeaderVo(
            title = StringModel.String("Main page"),
            onBackClick = {},
            endIcons = listOf(
                IconButtonVo(
                    iconVo = IconVo(
                        res = R.drawable.ic_add
                    ),
                    onClick = {}
                ),
                IconButtonVo(
                    iconVo = IconVo(
                        res = R.drawable.ic_cancel
                    ),
                    onClick = {}
                )
            )
        ),
    )
}