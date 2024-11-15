package cz.cvut.fukalhan.swap.events.model.addevent

import cz.cvut.fukalhan.swap.events.view.AddEventScreen

/**
 * View object for [AddEventScreen]
 *
 * @property name event name
 * @property description event description
 * @property dateTime date and time of event
 * @property showDatePicker determine if date picker is visible
 * @property saveButtonEnabled determine if the save button is enabled
 */
data class AddEventScreenVo(
    val name: String = "",
    val description: String = "",
    val dateTime: String = "",
    val location: String = "",
    val showDatePicker: Boolean = false
) {
    val saveButtonEnabled: Boolean =
        name.isNotBlank() && description.isNotBlank() && dateTime.isNotBlank() && location.isNotBlank()

    companion object {
        const val DESCRIPTION_CHAR_LIMIT = 200
    }
}