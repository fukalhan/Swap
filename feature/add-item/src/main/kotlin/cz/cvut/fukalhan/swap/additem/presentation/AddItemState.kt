package cz.cvut.fukalhan.swap.additem.presentation

import cz.cvut.fukalhan.swap.additem.R

sealed class AddItemState(val messageRes: Int)

object PendingState : AddItemState(R.string.empty)
object SuccessfulState : AddItemState(R.string.itemSaveSuccess)
object FailedState : AddItemState(R.string.itemSaveFail)
