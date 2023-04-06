package cz.cvut.fukalhan.swap.login.presentation

import cz.cvut.fukalhan.swap.auth.model.SignUpResponse
import cz.cvut.fukalhan.swap.login.R

fun SignUpResponse.toSignUpState(): SignUpState {
    var showMessage = true
    val messageResId = when (this) {
        SignUpResponse.WEAK_PASSWORD -> R.string.weakPassword
        SignUpResponse.EMAIL_ALREADY_REGISTERED -> R.string.emailTaken
        SignUpResponse.FAIL -> R.string.signUpError
        else -> {
            showMessage = false
            0
        }
    }
    return SignUpState(showMessage, messageResId)
}
