package cz.cvut.fukalhan.design.system.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cz.cvut.fukalhan.design.system.model.DatePickerVo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    model: DatePickerVo,
    onDismiss: () -> Unit,
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

        }
    ) {
        androidx.compose.material3.DatePicker(
            state = state
        )
    }
}

@Composable
@Preview
internal fun DatePickerPreview() {
    DatePicker(
        model = DatePickerVo(),
        onDismiss = {}
    )
}