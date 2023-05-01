package cz.cvut.fukalhan.swap.events.system.addevent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.tools.DateFormatter
import org.koin.androidx.compose.getKoin
import java.time.LocalDate

@Composable
fun PickDateRow(
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
        Column(
            modifier = Modifier.weight(1f)
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
    val disabledDates = listOf(
        LocalDate.now().minusDays(7),
        LocalDate.now().plusDays(3)
    )

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
