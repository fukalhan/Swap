package cz.cvut.fukalhan.swap.login.presentation.common

class LoginState(
    val result: State = State.PENDING,
    val messageResId: Int = 0
) {
    fun resolve(onSuccessResultAction: () -> Unit, onFailedResultAction: () -> Unit) {
        when (result) {
            State.SUCCESS -> onSuccessResultAction()
            State.FAILED -> onFailedResultAction()
            else -> {}
        }
    }

    enum class State {
        SUCCESS, FAILED, PENDING
    }
}
