package cz.cvut.fukalhan.swap.itemdata.model

import android.net.Uri

data class ItemDetail(
    var id: String = "",
    val ownerId: String = "",
    var name: String = "",
    var description: String = "",
    var imagesUri: List<Uri> = emptyList(),
    var category: Category = Category.DEFAULT,
    var state: State = State.AVAILABLE,
    var isLiked: Boolean = false
)
