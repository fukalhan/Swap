package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.model.signin.SignInCredentials
import cz.cvut.fukalhan.swap.auth.model.signin.SignInResult
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpResult

interface Repository {
    suspend fun signUpUser(credentials: SignUpCredentials): SignUpResult

    suspend fun signInUser(credentials: SignInCredentials): SignInResult
}
