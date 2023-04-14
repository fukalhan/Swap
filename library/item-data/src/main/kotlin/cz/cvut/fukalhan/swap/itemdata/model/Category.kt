package cz.cvut.fukalhan.swap.itemdata.model

import cz.cvut.fukalhan.swap.itemdata.R

enum class Category(val labelId: Int) {
    DEFAULT(R.string.category),
    WOMEN_CLOTHES(R.string.womenClothes),
    MEN_CLOTHES(R.string.menClothes),
    CHILDREN_CLOTHES(R.string.childrenClothes),
    COSMETICS(R.string.cosmetics),
    ACCESSORIES(R.string.accessories),
    HOUSEHOLD_SUPPLIES(R.string.householdSupplies),
    APPLIANCES(R.string.appliances),
    ELECTRONICS(R.string.electronics),
    TOYS(R.string.toys),
    BOOKS_MOVIES_MUSIC(R.string.booksMoviesMusic)
}

val categories = listOf(
    Category.WOMEN_CLOTHES,
    Category.MEN_CLOTHES,
    Category.CHILDREN_CLOTHES,
    Category.ACCESSORIES,
    Category.COSMETICS,
    Category.APPLIANCES,
    Category.HOUSEHOLD_SUPPLIES,
    Category.APPLIANCES,
    Category.ELECTRONICS,
    Category.TOYS,
    Category.BOOKS_MOVIES_MUSIC
)