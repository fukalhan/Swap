package cz.cvut.fukalhan.swap.userdata.model

data class Review(
    var id: String = "",
    val userId: String = "",
    val reviewerId: String = "",
    val rating: Int = 0,
    val description: String = ""
)
