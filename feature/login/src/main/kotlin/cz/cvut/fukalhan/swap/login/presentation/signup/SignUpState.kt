package cz.cvut.fukalhan.swap.login.presentation.signup

class SignUpState(
    val result: State = State.BEFORE_SIGN_UP,
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
        SUCCESS, FAILED, BEFORE_SIGN_UP
    }
}
