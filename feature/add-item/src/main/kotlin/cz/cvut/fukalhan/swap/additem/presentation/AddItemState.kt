package cz.cvut.fukalhan.swap.additem.presentation

import cz.cvut.fukalhan.design.R

sealed class AddItemState
data object Init : AddItemState()
data object Loading : AddItemState()
class Success(val message: Int = R.string.itemSaveSuccess) : AddItemState()
class Failure(val message: Int = R.string.itemSaveFail) : AddItemState()
