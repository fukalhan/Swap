package cz.cvut.fukalhan.swap.login.presentation.signup

import cz.cvut.fukalhan.swap.auth.model.signup.SignUpResult
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState

fun SignUpResult.toLoginState(): LoginState {
    return when (this) {
        SignUpResult.SERVICE_UNAVAILABLE -> LoginState(LoginState.State.FAILED, R.string.serviceUnavailable)
        SignUpResult.USERNAME_TAKEN -> LoginState(LoginState.State.FAILED, R.string.usernameTaken)
        SignUpResult.WEAK_PASSWORD -> LoginState(LoginState.State.FAILED, R.string.weakPassword)
        SignUpResult.EMAIL_ALREADY_REGISTERED -> LoginState(LoginState.State.FAILED, R.string.emailTaken)
        SignUpResult.FAIL -> LoginState(LoginState.State.FAILED, R.string.loginFailed)
        else -> LoginState(LoginState.State.SUCCESS, R.string.loginSuccess)
    }
}
