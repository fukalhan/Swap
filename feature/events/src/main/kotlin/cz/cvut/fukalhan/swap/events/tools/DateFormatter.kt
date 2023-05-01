package cz.cvut.fukalhan.swap.events.tools

import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.events.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateFormatter(private val stringResources: StringResources) {

    fun formatEventDate(selectedDates: List<LocalDate>): String {
        val formatter = DateTimeFormatter.ofPattern("d.M.yyyy")

        return if (selectedDates.isEmpty()) {
            stringResources.getString(R.string.emptyDate)
        } else {
            if (selectedDates.size > 1) {
                stringResources.getString(
                    R.string.dateRange,
                    selectedDates.first().format(formatter),
                    selectedDates.last().format(formatter)
                )
            } else {
                selectedDates.first().format(formatter)
            }
        }
    }
}
