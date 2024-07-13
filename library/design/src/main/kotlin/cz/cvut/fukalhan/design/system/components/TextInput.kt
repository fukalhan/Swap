package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.CharCounterVo
import cz.cvut.fukalhan.design.system.model.TextInputVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

@Composable
fun TextInput(
    model: TextInputVo,
    modifier: Modifier = Modifier,
) {
    Column {
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

        TextField(
            modifier = modifier.fillMaxWidth(),
            value = model.value,
            onValueChange = model.onValueChange,
            enabled = model.enabled,
            shape = RoundedCornerShape(12.dp),
            isError = model.isError,
            textStyle = model.textStyle,
            placeholder = {
                (model.placeholder ?: model.label)?.let {
                    if (model.setLabelAsPlaceholder) it else null
                }?.let { text ->
                    Text(
                        text = text.getString(),
                        style = SwapAppTheme.typography.body,
                        color = SwapAppTheme.colors.onBackground
                    )
                }
            },
            leadingIcon = model.startIcon?.let { icon ->
                {
                    Icon(
                        model = icon
                    )
                }
            },
            trailingIcon = model.endIcon?.let { icon ->
                {
                    Icon(
                        model = icon
                    )
                }
            },
            prefix = model.prefix,
            suffix = model.suffix,
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (model.isError) {
                            model.errorText
                        } else {
                            model.supportingText
                        }?.let { text ->
                            Text(
                                text = text.getString(),
                                style = SwapAppTheme.typography.button,
                                color = if (model.isError) {
                                    SwapAppTheme.colors.error
                                } else {
                                    SwapAppTheme.colors.onBackground
                                }
                            )
                        }
                    }

                    model.charCounter?.let {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "${it.current}/${it.limit}",
                            style = SwapAppTheme.typography.button,
                            color = if (model.isError) {
                                SwapAppTheme.colors.error
                            } else {
                                SwapAppTheme.colors.onBackground
                            }
                        )
                    }
                }
            },
            visualTransformation = model.visualTransformation,
            keyboardOptions = model.keyboardOptions,
            keyboardActions = model.keyboardActions,
            singleLine = model.singleLine,
            minLines = model.minLines,
            maxLines = model.maxLines,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = SwapAppTheme.colors.onBackground,
                errorTextColor = SwapAppTheme.colors.error,
                focusedContainerColor = SwapAppTheme.colors.onBackground.copy(
                    alpha = 0.5f
                ),
                unfocusedContainerColor = SwapAppTheme.colors.secondaryVariant,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = SwapAppTheme.colors.primary,
                errorCursorColor = SwapAppTheme.colors.error
            )
        )
    }
}

@Preview
@Composable
internal fun TextInputPreview() {
    TextInput(
        model = TextInputVo(
            value = "Input",
            onValueChange = {},
            label = StringModel.String("Label"),
            supportingText = StringModel.String("Supporting text"),
            charCounter = CharCounterVo(
                current = 0,
                limit = 20
            )
        )
    )
}