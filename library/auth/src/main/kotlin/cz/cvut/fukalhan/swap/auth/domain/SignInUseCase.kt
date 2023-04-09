package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.signin.SignInCredentials
import cz.cvut.fukalhan.swap.auth.model.signin.SignInResult

class SignInUseCase(private val repository: Repository) {
    suspend fun signInUser(credentials: SignInCredentials): SignInResult {
        return repository.signInUser(credentials)
    }
}
