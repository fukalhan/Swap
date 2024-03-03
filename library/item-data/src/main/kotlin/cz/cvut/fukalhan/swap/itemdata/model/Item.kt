package cz.cvut.fukalhan.swap.itemdata.model

import android.net.Uri

data class Item(
    var id: String = "",
    val ownerId: String = "",
    var name: String = "",
    var description: String = "",
    var imagesUri: List<Uri> = emptyList(),
    var category: Category = Category.OTHER,
    var state: State = State.AVAILABLE,
)
