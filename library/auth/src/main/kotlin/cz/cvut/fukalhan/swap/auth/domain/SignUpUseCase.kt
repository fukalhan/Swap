package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.signup.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpResult

class SignUpUseCase(private val repository: Repository) {

    suspend fun signUpUser(signUpCredentials: SignUpCredentials): SignUpResult {
        return repository.signUpUser(signUpCredentials)
    }
}
