package cz.cvut.fukalhan.swap.additem.presentation

import cz.cvut.fukalhan.swap.itemdata.domain.SaveItemResponse

fun SaveItemResponse.toAddItemState(): AddItemState {
    return when (this) {
        SaveItemResponse.SUCCESS -> SuccessfulState
        else -> FailedState
    }
}
