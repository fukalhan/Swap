package cz.cvut.fukalhan.design.presentation

/**
 * State for the UI composable screen
 *
 * @param data data to be displayed on the screen
 * @param loading determine if show loading or not
 * @param resultModel contains data about occurred result (e.g. error, warning,...),
 * null when there is no result
 */
data class UiState<T>(
    val data: T,
    val loading: Boolean = false,
    val resultModel: ResultModel? = null
)