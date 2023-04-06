package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.SignUpResponse

interface Repository {
    suspend fun signUpUser(signUpCredentials: SignUpCredentials): SignUpResponse
}
