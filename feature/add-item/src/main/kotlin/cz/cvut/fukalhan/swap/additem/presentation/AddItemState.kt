package cz.cvut.fukalhan.swap.additem.presentation

import cz.cvut.fukalhan.swap.additem.R

sealed class AddItemState
object Init : AddItemState()
object Loading : AddItemState()
class Success(val message: Int = R.string.itemSaveSuccess) : AddItemState()
class Failure(val message: Int = R.string.itemSaveFail) : AddItemState()
