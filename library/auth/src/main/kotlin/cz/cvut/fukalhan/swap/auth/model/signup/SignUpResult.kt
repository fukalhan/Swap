package cz.cvut.fukalhan.swap.auth.model.signup

enum class SignUpResult {
    SUCCESS, FAIL, WEAK_PASSWORD, EMAIL_ALREADY_REGISTERED, USERNAME_TAKEN, SERVICE_UNAVAILABLE
}
