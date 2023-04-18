package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.signup.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpResult

class SignUpUseCase(private val authRepository: AuthRepository) {

    suspend fun signUpUser(signUpCredentials: SignUpCredentials): SignUpResult {
        return authRepository.signUpUser(signUpCredentials)
    }
}
