package cz.cvut.fukalhan.swap.auth.data

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.auth.domain.AuthRepository
import cz.cvut.fukalhan.swap.auth.model.signin.SignInCredentials
import cz.cvut.fukalhan.swap.auth.model.signin.SignInResult
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.signup.SignUpResult
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository : AuthRepository {
    private val functions = Firebase.functions
    private val auth = Firebase.auth

    override suspend fun signUpUser(credentials: SignUpCredentials): SignUpResult {
        val data = hashMapOf(
            EMAIL to credentials.email,
            USERNAME to credentials.username,
            PASSWORD to credentials.password
        )

        return try {
            val httpsCallableResult = functions
                .getHttpsCallable(CREATE_USER)
                .call(data)
                .await()

            val result = httpsCallableResult.data as Map<*, *>

            when (result[RESULT]) {
                WEAK_PASSWORD -> SignUpResult.WEAK_PASSWORD
                USERNAME_TAKEN -> SignUpResult.USERNAME_TAKEN
                EMAIL_ALREADY_EXISTS -> SignUpResult.EMAIL_ALREADY_REGISTERED
                SERVICE_UNAVAILABLE -> SignUpResult.SERVICE_UNAVAILABLE
                REQUEST_FAILED -> SignUpResult.FAIL
                else -> {
                    auth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
                    SignUpResult.SUCCESS
                }
            }
        } catch (e: FirebaseFunctionsException) {
            SignUpResult.FAIL
        }
    }

    override suspend fun signInUser(credentials: SignInCredentials): SignInResult {
        return try {
            auth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
            SignInResult.SUCCESS
        } catch (e: FirebaseAuthException) {
            when (e) {
                is FirebaseAuthInvalidUserException -> SignInResult.NOT_EXISTING_ACCOUNT
                is FirebaseAuthInvalidCredentialsException -> SignInResult.WRONG_PASSWORD
                else -> SignInResult.FAIL
            }
        }
    }
}
