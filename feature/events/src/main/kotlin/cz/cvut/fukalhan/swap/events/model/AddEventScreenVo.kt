package cz.cvut.fukalhan.swap.events.model

data class AddEventScreenVo(
    val name: String = "",
    val description: String = "",
    val dateTime: String = "",
    val location: String = ""
) {
    companion object {
        const val DESCRIPTION_CHAR_LIMIT = 200
    }
}