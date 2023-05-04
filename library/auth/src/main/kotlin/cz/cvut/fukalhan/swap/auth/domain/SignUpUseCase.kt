package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.data.Response
import cz.cvut.fukalhan.swap.auth.data.SignUpResult
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend fun signUpUser(signUpCredentials: SignUpCredentials): Response<SignUpResult> {
        return authRepository.signUpUser(signUpCredentials)
    }
}
