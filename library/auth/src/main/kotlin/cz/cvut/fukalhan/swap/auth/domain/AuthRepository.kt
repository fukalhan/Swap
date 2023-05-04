package cz.cvut.fukalhan.swap.auth.domain

import cz.cvut.fukalhan.swap.auth.data.Response
import cz.cvut.fukalhan.swap.auth.data.SignInResult
import cz.cvut.fukalhan.swap.auth.data.SignUpResult
import cz.cvut.fukalhan.swap.auth.model.SignInCredentials
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials

interface AuthRepository {
    suspend fun signUpUser(credentials: SignUpCredentials): Response<SignUpResult>
    suspend fun signInUser(credentials: SignInCredentials): Response<SignInResult>
    suspend fun getStreamChatUserToken(): String?
}
