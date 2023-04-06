package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials

class SignUpUserUseCase(private val repository: Repository) {

    suspend fun signUpUser(signUpCredentials: SignUpCredentials) {
        repository.signUpUser(signUpCredentials)
    }
}
