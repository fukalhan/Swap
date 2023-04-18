package cz.cvut.fukalhan.swap.profile.presentation.items

import android.net.Uri
import cz.cvut.fukalhan.swap.profile.R

sealed class ItemListState

object Init : ItemListState()
object Loading : ItemListState()

class Success(
    val items: List<ItemState> = emptyList()
) : ItemListState()

data class ItemState(
    val imageUri: Uri,
    val name: String,
)

class Failure(val message: Int = R.string.cannotLoadItems) : ItemListState()
