package cz.cvut.fukalhan.swap.events.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.DescriptionView
import cz.cvut.fukalhan.design.system.components.InputFieldView
import cz.cvut.fukalhan.design.system.components.RegularTextFieldView
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.AddEventViewModel
import cz.cvut.fukalhan.swap.events.tools.DateFormatter
import org.koin.androidx.compose.getKoin
import java.time.LocalDate

const val DESCRIPTION_CHAR_LIMIT = 200

@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit
) {
    AddEventTopBar(onScreenInit, navigateBack)

    Box(modifier = Modifier.fillMaxSize()) {
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
    var title by remember { mutableStateOf("") }
    val calendarState = rememberUseCaseState(visible = false)
    var selectedDates by remember { mutableStateOf<List<LocalDate>>(emptyList()) }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
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

        DatePickRow(selectedDates) {
            calendarState.show()
        }

        CalendarPicker(calendarState) {
            selectedDates = it
        }
    }
}

@Composable
fun DatePickRow(
    selectedDates: List<LocalDate>,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.date),
                style = SwapAppTheme.typography.titleSecondary,
                color = SwapAppTheme.colors.textPrimary
            )
            Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.smallSpacer))

            val date = DateFormatter(getKoin().get()).formatEventDate(selectedDates)

            Text(
                text = date,
                style = SwapAppTheme.typography.body,
                color = SwapAppTheme.colors.textSecondary
            )
        }

        Surface(
            shape = CircleShape,
            elevation = SwapAppTheme.dimensions.elevation
        ) {
            IconButton(
                onClick = onButtonClick,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SwapAppTheme.colors.primary)
            ) {
                Icon(painter = painterResource(R.drawable.edit_calendar), null, tint = SwapAppTheme.colors.buttonText)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPicker(
    calendarState: UseCaseState,
    updateDates: (List<LocalDate>) -> Unit,
) {
    val disabledDates = listOf(LocalDate.now().minusDays(3))

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Dates { newDates ->
            updateDates(newDates)
        },
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = disabledDates
        ),
    )
}
