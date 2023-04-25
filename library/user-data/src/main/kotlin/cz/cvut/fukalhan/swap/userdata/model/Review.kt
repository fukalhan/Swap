package cz.cvut.fukalhan.swap.userdata.model

data class Review(
    var id: String = "",
    val userId: String,
    val reviewerId: String,
    val value: Int,
    val description: String
)
