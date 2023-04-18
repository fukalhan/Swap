package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.signin.SignInCredentials
import cz.cvut.fukalhan.swap.auth.model.signin.SignInResult

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend fun signInUser(credentials: SignInCredentials): SignInResult {
        return authRepository.signInUser(credentials)
    }
}
