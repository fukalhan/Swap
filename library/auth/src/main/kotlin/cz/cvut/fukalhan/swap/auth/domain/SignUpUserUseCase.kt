package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.SignUpResponse

class SignUpUserUseCase(private val repository: Repository) {

    suspend fun signUpUser(signUpCredentials: SignUpCredentials): SignUpResponse {
        return repository.signUpUser(signUpCredentials)
    }
}
