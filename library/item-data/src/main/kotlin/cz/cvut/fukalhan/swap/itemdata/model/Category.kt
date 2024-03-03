package cz.cvut.fukalhan.swap.itemdata.model

import cz.cvut.fukalhan.swap.itemdata.R
enum class Category(
    val id: Long,
    val labelId: Int
) {
    WOMEN_CLOTHES(1, R.string.womenClothes),
    MEN_CLOTHES(2, R.string.menClothes),
    CHILDREN_CLOTHES(3, R.string.childrenClothes),
    COSMETICS(4, R.string.cosmetics),
    ACCESSORIES(5, R.string.accessories),
    HOUSEHOLD_SUPPLIES(6, R.string.householdSupplies),
    APPLIANCES(7, R.string.appliances),
    ELECTRONICS(8, R.string.electronics),
    TOYS(9, R.string.toys),
    BOOKS_MOVIES_MUSIC(10, R.string.booksMoviesMusic),
    SHOES(11, R.string.shoes),
    SPORT_EQUIPMENT(12, R.string.sportsEquipment),
    GARDEN_EQUIPMENT(13, R.string.gardenEquipment),
    PLANTS(14, R.string.plants),
    OTHER(15, R.string.others)
}

val categories = listOf(
    Category.WOMEN_CLOTHES,
    Category.MEN_CLOTHES,
    Category.CHILDREN_CLOTHES,
    Category.SHOES,
    Category.ACCESSORIES,
    Category.COSMETICS,
    Category.APPLIANCES,
    Category.HOUSEHOLD_SUPPLIES,
    Category.GARDEN_EQUIPMENT,
    Category.PLANTS,
    Category.ELECTRONICS,
    Category.TOYS,
    Category.SPORT_EQUIPMENT,
    Category.BOOKS_MOVIES_MUSIC,
    Category.OTHER
)
