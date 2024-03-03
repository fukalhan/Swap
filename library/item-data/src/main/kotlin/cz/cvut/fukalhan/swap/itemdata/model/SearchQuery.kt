package cz.cvut.fukalhan.swap.itemdata.model

data class SearchQuery(
    var searchString: String = "",
    var sorting: Sorting = Sorting.DEFAULT,
    var category: Category = Category.OTHER
)
