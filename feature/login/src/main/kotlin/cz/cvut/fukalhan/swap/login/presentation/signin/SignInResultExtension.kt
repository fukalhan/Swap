package cz.cvut.fukalhan.swap.login.presentation.signin

import cz.cvut.fukalhan.swap.auth.model.signin.SignInResult
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState

fun SignInResult.toLoginState(): LoginState {
    return when (this) {
        SignInResult.NOT_EXISTING_ACCOUNT -> LoginState(LoginState.State.FAILED, R.string.nonExistantAccount)
        SignInResult.WRONG_PASSWORD -> LoginState(LoginState.State.FAILED, R.string.wrongPassword)
        SignInResult.FAIL -> LoginState(LoginState.State.FAILED, R.string.loginFailed)
        else -> LoginState(LoginState.State.SUCCESS, R.string.loginSuccess)
    }
}
