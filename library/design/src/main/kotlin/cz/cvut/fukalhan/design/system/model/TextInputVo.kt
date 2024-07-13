package cz.cvut.fukalhan.design.system.model

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.TextInput

/**
 * View object for the [TextInput] component
 *
 * @property value text input value
 * @property onValueChange callback when value is changed
 * @property enabled determine if the input is enabled
 * @property readOnly determine if the input is read-only
 * @property isError determine if the value causes error state
 * @property textStyle text style of the input
 * @property label optional label of the input
 * @property placeholder optional placeholder to be displayed when the input is empty
 * @property setLabelAsPlaceholder determine if the label should be set as placeholder when
 * the placeholder is not present and the input is empty
 * @property startIcon optional icon at the beginning of the input
 * @property endIcon optional icon at the end of the input
 * @property prefix optional prefix of the input
 * @property suffix optional suffix of the input
 * @property supportingText optional supporting text to be displayed under the input
 * @property errorText optional error text to be displayed under the input in error statr
 * @property visualTransformation optional visual transformation of the input
 * @property keyboardOptions keyboard options
 * @property keyboardActions keyboard actions
 * @property singleLine determine if the input is single line
 * @property maxLines if the input is not single line, determine how many lines it should be
 * @property minLines determine minimum number of lines
 * @property charCounter view object for char counter to be displayed under the input at the end of
 * the view
 */
data class TextInputVo(
    val value: String,
    val onValueChange: (String) -> Unit,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val isError: Boolean = false,
    val textStyle: TextStyle = SwapAppTheme.typography.labelText,
    val label: StringModel? = null,
    val placeholder: StringModel? = null,
    val setLabelAsPlaceholder: Boolean = true,
    val startIcon: IconVo? = null,
    val endIcon: IconVo? = null,
    val prefix: @Composable (() -> Unit)? = null,
    val suffix: @Composable (() -> Unit)? = null,
    val supportingText: StringModel? = null,
    val errorText: StringModel? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val singleLine: Boolean = true,
    val maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    val minLines: Int = 1,
    val charCounter: CharCounterVo? = null,
)

/**
 * View object for the character counter for the [TextInput]
 *
 * @property limit max number of characters allowed
 * @property current current number of characters
 */
data class CharCounterVo(
    val limit: Int,
    val current: Int = 0
)