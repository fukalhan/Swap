package cz.cvut.fukalhan.swap.events.system.addevent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.DescriptionView
import cz.cvut.fukalhan.design.system.components.InputFieldView
import cz.cvut.fukalhan.design.system.components.RegularTextFieldView
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.AddEventViewModel
import java.time.LocalDate

const val DESCRIPTION_CHAR_LIMIT = 200

@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit
) {
    AddEventTopBar(onScreenInit, navigateBack)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AddEvent()
    }
}

@Composable
fun AddEventTopBar(
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.addEvent),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun AddEvent() {
    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("") }
    val calendarState = rememberUseCaseState(visible = false)
    var selectedDates by remember { mutableStateOf<List<LocalDate>>(emptyList()) }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        InputFieldView(label = R.string.title) {
            RegularTextFieldView(
                label = R.string.titlePlaceholder,
                value = title
            ) {
                title = it
            }
        }

        InputFieldView(label = R.string.description) {
            DescriptionView(
                label = R.string.eventDescriptionPlaceholder,
                charLimit = DESCRIPTION_CHAR_LIMIT,
                description = description
            ) {
                description = it
            }
        }

        PickDateRow(selectedDates) {
            calendarState.show()
        }

        CalendarPicker(calendarState) {
            selectedDates = it
        }

        Spacer(modifier = Modifier.height(SwapAppTheme.dimensions.smallSpacer))
        AddressInputView {
        }
    }
}
