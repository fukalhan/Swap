package cz.cvut.fukalhan.swap.profile.presentation.items

import android.net.Uri

data class ItemListState(
    val items: List<ItemState> = emptyList()
)

data class ItemState(
    val imageUri: Uri,
    val name: String,
)
