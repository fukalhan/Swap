package cz.cvut.fukalhan.swap.login.presentation.signup

import cz.cvut.fukalhan.swap.auth.model.SignUpResult
import cz.cvut.fukalhan.swap.login.R

fun SignUpResult.toSignUpState(): SignUpState {
    return when (this) {
        SignUpResult.SERVICE_UNAVAILABLE -> SignUpState(SignUpState.State.FAILED, R.string.servieUnavailable)
        SignUpResult.USERNAME_TAKEN -> SignUpState(SignUpState.State.FAILED, R.string.usernameTaken)
        SignUpResult.WEAK_PASSWORD -> SignUpState(SignUpState.State.FAILED, R.string.weakPassword)
        SignUpResult.EMAIL_ALREADY_REGISTERED -> SignUpState(SignUpState.State.FAILED, R.string.emailTaken)
        SignUpResult.FAIL -> SignUpState(SignUpState.State.FAILED, R.string.signUpError)
        else -> SignUpState(SignUpState.State.SUCCESS, 0)
    }
}
