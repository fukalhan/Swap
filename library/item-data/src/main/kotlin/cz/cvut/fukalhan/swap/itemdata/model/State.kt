package cz.cvut.fukalhan.swap.itemdata.model

import cz.cvut.fukalhan.swap.itemdata.R

enum class State(val label: Int) {
    AVAILABLE(R.string.available),
    RESERVED(R.string.reserved),
    SWAPPED(R.string.swapped)
}
