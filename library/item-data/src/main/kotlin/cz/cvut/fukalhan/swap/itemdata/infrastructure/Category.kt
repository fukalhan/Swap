package cz.cvut.fukalhan.swap.itemdata.infrastructure

import cz.cvut.fukalhan.swap.itemdata.R

sealed class Category(val labelId: Int)

object Default : Category(R.string.category)
object WomenClothes : Category(R.string.womenClothes)
object MenClothes : Category(R.string.menClothes)
object ChildrenClothes : Category(R.string.childrenClothes)
object Cosmetics : Category(R.string.cosmetics)
object Accessories : Category(R.string.accessories)
object HouseholdSupplies : Category(R.string.householdSupplies)
object Appliances : Category(R.string.appliances)
object Electronics : Category(R.string.electronics)
object Toys : Category(R.string.toys)
object BooksMoviesMusic : Category(R.string.booksMoviesMusic)

val categories = listOf(
    WomenClothes,
    MenClothes,
    ChildrenClothes,
    Cosmetics,
    Accessories,
    HouseholdSupplies,
    Appliances,
    Electronics,
    Toys,
    BooksMoviesMusic
)