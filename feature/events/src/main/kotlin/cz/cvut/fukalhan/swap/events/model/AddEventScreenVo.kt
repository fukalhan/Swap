package cz.cvut.fukalhan.swap.events.model

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