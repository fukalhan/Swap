package cz.cvut.fukalhan.swap.additem.system.helperviews

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
import cz.cvut.fukalhan.swap.additem.R
import cz.cvut.fukalhan.swap.additem.system.DESCRIPTION_CHAR_LIMIT

@Composable
fun InputFieldView(
    labelRes: Int,
    textField: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(labelRes),
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
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(R.string.namePlaceholder),
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
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Box {
        TextField(
            value = description,
            onValueChange = { newValue ->
                if (newValue.length <= DESCRIPTION_CHAR_LIMIT) {
                    onDescriptionChange(newValue)
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.descriptionPlaceholder),
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
            text = "${description.length}/$DESCRIPTION_CHAR_LIMIT",
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
