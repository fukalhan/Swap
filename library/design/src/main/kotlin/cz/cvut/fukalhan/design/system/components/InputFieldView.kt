package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun InputFieldView(
    label: Int,
    textField: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(label),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary,
            modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
        )
        textField()
        Spacer(modifier = Modifier.size(SwapAppTheme.dimensions.smallSpacer))
    }
}

@Composable
fun RegularTextFieldView(
    label: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(label),
                style = SwapAppTheme.typography.body,
                color = SwapAppTheme.colors.textSecondary
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        colors = getTextFieldColors(),
        modifier = inputFieldModifier,
        keyboardOptions = keyBoardOptions
    )
}

@Composable
fun DescriptionView(
    label: Int,
    charLimit: Int,
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Box {
        TextField(
            value = description,
            onValueChange = { newValue ->
                if (newValue.length <= charLimit) {
                    onDescriptionChange(newValue)
                }
            },
            placeholder = {
                Text(
                    text = stringResource(label),
                    style = SwapAppTheme.typography.body,
                    color = SwapAppTheme.colors.textSecondary
                )
            },
            modifier = inputFieldModifier.height(120.dp),
            maxLines = Int.MAX_VALUE,
            colors = getTextFieldColors(),
            keyboardOptions = keyBoardOptions
        )

        Text(
            text = "${description.length}/$charLimit",
            style = SwapAppTheme.typography.body,
            color = SwapAppTheme.colors.textPrimary,
            modifier = Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .align(Alignment.BottomEnd)
        )
    }
}

val inputFieldModifier = Modifier
    .fillMaxWidth()
    .padding(SwapAppTheme.dimensions.smallSidePadding)
    .border(
        width = SwapAppTheme.dimensions.borderWidth,
        color = SwapAppTheme.colors.component,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
    )
    .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))

val keyBoardOptions = KeyboardOptions(
    keyboardType = KeyboardType.Text,
    capitalization = KeyboardCapitalization.Sentences,
    autoCorrect = true
)

@Composable
fun getTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        cursorColor = SwapAppTheme.colors.primary,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
}
