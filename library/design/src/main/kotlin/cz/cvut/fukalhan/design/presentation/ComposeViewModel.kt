package cz.cvut.fukalhan.design.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for view models of composable screens
 *
 * @property viewState state of the composable screen
 * @property onEvent function to handle events from the view
 */
interface ComposeViewModel<T, E> {

    val viewState: StateFlow<UiState<T>>

    fun onEvent(event: E)
}

/**
 * View model for composable previews
 */
class PreviewViewModel<T, E>(val state: UiState<T>) : ComposeViewModel<T, E> {

    override val viewState: StateFlow<UiState<T>> = MutableStateFlow(state)

    override fun onEvent(event: E) {}

}