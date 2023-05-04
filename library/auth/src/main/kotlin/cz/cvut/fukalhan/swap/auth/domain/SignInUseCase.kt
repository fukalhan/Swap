package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.data.Response
import cz.cvut.fukalhan.swap.auth.data.SignInResult
import cz.cvut.fukalhan.swap.auth.model.SignInCredentials

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend fun signInUser(credentials: SignInCredentials): Response<SignInResult> {
        return authRepository.signInUser(credentials)
    }
}
