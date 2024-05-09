package cz.cvut.fukalhan.design.presentation

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