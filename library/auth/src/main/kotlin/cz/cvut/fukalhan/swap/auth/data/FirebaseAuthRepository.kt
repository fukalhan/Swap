package cz.cvut.fukalhan.swap.auth.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.auth.domain.AuthRepository
import cz.cvut.fukalhan.swap.auth.model.SignInCredentials
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository : AuthRepository {
    private val functions = Firebase.functions
    private val auth = Firebase.auth

    override suspend fun signUpUser(credentials: SignUpCredentials): Response<SignUpResult> {
        val data = hashMapOf(
            EMAIL to credentials.email,
            USERNAME to credentials.username,
            PASSWORD to credentials.password,
            FCM_TOKEN to credentials.fcmToken
        )

        return try {
            val httpsCallableResult = functions
                .getHttpsCallable(CREATE_USER)
                .call(data)
                .await()

            val result = httpsCallableResult.data as Map<*, *>

            val code = when (result[RESULT]) {
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
            Response(code)
        } catch (e: FirebaseFunctionsException) {
            Log.e("signUpUser", "Exception $e")
            Response(SignUpResult.FAIL)
        }
    }

    override suspend fun signInUser(credentials: SignInCredentials): Response<SignInResult> {
        return try {
            auth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
            Response(SignInResult.SUCCESS)
        } catch (e: FirebaseAuthException) {
            val code = when (e) {
                is FirebaseAuthInvalidUserException -> SignInResult.NOT_EXISTING_ACCOUNT
                is FirebaseAuthInvalidCredentialsException -> SignInResult.WRONG_PASSWORD
                else -> SignInResult.FAIL
            }
            Log.e("signInUser", "Exception $e")
            Response(code)
        }
    }

    override suspend fun getStreamChatUserToken(): String? {
        val httpsCallableResult = functions.getHttpsCallable(GET_STREAM_USER_TOKEN).call().await()
        val result = httpsCallableResult.data
        return result?.toString()
    }
}
