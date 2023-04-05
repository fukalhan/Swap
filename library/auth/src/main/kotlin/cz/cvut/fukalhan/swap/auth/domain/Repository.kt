package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials

interface Repository {
    suspend fun signUpUser(signUpCredentials: SignUpCredentials)
}