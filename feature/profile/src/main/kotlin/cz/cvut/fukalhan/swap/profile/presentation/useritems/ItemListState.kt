package cz.cvut.fukalhan.swap.profile.presentation.useritems

import android.net.Uri

data class ItemListState(
    val items: List<ItemState> = emptyList()
)

data class ItemState(
    val imageUri: Uri,
    val name: String,
)
