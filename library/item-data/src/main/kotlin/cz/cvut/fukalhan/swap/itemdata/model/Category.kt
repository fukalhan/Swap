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
    BOOKS_MOVIES_MUSIC(R.string.booksMoviesMusic),
    SHOES(R.string.shoes),
    SPORT_EQUIPMENT(R.string.sportsEquipment),
    GARDEN_EQUIPMENT(R.string.gardenEquipment),
    PLANTS(R.string.plants),
    OTHER(R.string.others)
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
    Category.APPLIANCES,
    Category.ELECTRONICS,
    Category.TOYS,
    Category.SPORT_EQUIPMENT,
    Category.BOOKS_MOVIES_MUSIC,
    Category.OTHER
)
