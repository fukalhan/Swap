package cz.cvut.fukalhan.swap.auth.data

class Response<T> (val code: T)

enum class SignInResult {
    SUCCESS, FAIL, NOT_EXISTING_ACCOUNT, WRONG_PASSWORD
}

enum class SignUpResult {
    SUCCESS, FAIL, WEAK_PASSWORD, EMAIL_ALREADY_REGISTERED, USERNAME_TAKEN, SERVICE_UNAVAILABLE
}
