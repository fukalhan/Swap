package cz.cvut.fukalhan.design.wrappers

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.presentation.ResultModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.SnackbarMessage
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.theme.semiTransparentBlack

/**
 * Wrapper for the screen content and other views based on the current screen state (loading, error, ...)
 *
 * @param state the screen state
 * @param content main screen content to be displayed
 * @param onSuccessAction optional action on success state
 * @param onErrorAction optional action on error state
 * @param onWarningAction optional action on warning state
 */
@Composable
fun ScreenContentWrapper(
    state: UiState<*>,
    onSuccessAction: (() -> Unit)? = null,
    onErrorAction: (() -> Unit)? = null,
    onWarningAction: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    content()
    Crossfade(
        modifier = Modifier.fillMaxSize(),
        targetState = state,
        label = "screenStateAnimation"
    ) { targetState ->
        when(state.resultModel) {
            is ResultModel.Success -> {
                SnackbarMessage(message = state.resultModel.message)
                onSuccessAction?.invoke()
            }
            is ResultModel.Error -> {
                SnackbarMessage(message = state.resultModel.message)
                onErrorAction?.invoke()
            }
            is ResultModel.Warning -> {
                SnackbarMessage(message = state.resultModel.message)
                onWarningAction?.invoke()
            }
            else -> {
                if (targetState.loading) {
                    LoadingView(semiTransparentBlack)
                }
            }
        }
    }
}