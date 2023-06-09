package cz.cvut.fukalhan.swap.login.presentation.signup

import cz.cvut.fukalhan.swap.auth.data.Response
import cz.cvut.fukalhan.swap.auth.data.SignUpResult
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.Failure
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.common.Success

fun Response<SignUpResult>.toLoginState(): LoginState {
    return when (this.code) {
        SignUpResult.SERVICE_UNAVAILABLE -> Failure(R.string.serviceUnavailable)
        SignUpResult.USERNAME_TAKEN -> Failure(R.string.usernameTaken)
        SignUpResult.WEAK_PASSWORD -> Failure(R.string.weakPassword)
        SignUpResult.EMAIL_ALREADY_REGISTERED -> Failure(R.string.emailTaken)
        SignUpResult.FAIL -> Failure(R.string.loginFailed)
        else -> Success
    }
}
