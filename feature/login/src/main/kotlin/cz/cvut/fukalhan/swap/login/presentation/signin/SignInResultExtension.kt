package cz.cvut.fukalhan.swap.login.presentation.signin

import cz.cvut.fukalhan.swap.auth.data.Response
import cz.cvut.fukalhan.swap.auth.data.SignInResult
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.Failure
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.common.Success

fun Response<SignInResult>.toLoginState(): LoginState {
    return when (this.code) {
        SignInResult.NOT_EXISTING_ACCOUNT -> Failure(R.string.nonExistantAccount)
        SignInResult.WRONG_PASSWORD -> Failure(R.string.wrongPassword)
        SignInResult.FAIL -> Failure(R.string.loginFailed)
        else -> Success
    }
}
