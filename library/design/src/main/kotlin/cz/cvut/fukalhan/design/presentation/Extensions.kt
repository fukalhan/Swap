package cz.cvut.fukalhan.design.presentation

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Refresh UiState with just data part, clear error and loading
 */
fun <T> MutableStateFlow<UiState<T>>.hideAllOverlays() {
    value = UiState(
        data = this.value.data
    )
}