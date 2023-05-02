package cz.cvut.fukalhan.swap.events.tools

import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.events.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateFormatter(private val stringResources: StringResources) {
    private val formatter = DateTimeFormatter.ofPattern("d.M.yyyy")

    fun formatEventSelectedDate(selectedDates: List<LocalDate>): String {
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

    fun formatEventDate(selectedDates: List<Long>): String {
        return if (selectedDates.size > 1) {
            val start = selectedDates.min()
            val end = selectedDates.max()

            val startDay = Instant.ofEpochMilli(start).atZone(ZoneId.systemDefault()).toLocalDateTime().format(
                formatter
            )
            val endDay = Instant.ofEpochMilli(end).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)

            stringResources.getString(R.string.dateRange, startDay, endDay)
        } else {
            val instant = Instant.ofEpochMilli(selectedDates.first())
            instant.atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)
        }
    }
}
