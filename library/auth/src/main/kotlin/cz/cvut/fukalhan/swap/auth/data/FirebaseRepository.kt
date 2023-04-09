package cz.cvut.fukalhan.swap.auth.data

import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.auth.domain.Repository
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.SignUpResult
import kotlinx.coroutines.tasks.await

class FirebaseRepository : Repository {
    private val functions = Firebase.functions

    override suspend fun signUpUser(signUpCredentials: SignUpCredentials): SignUpResult {
        val data = hashMapOf(
            "email" to signUpCredentials.email,
            "username" to signUpCredentials.username,
            "password" to signUpCredentials.password
        )

        return try {
            val httpsCallableResult = functions
                .getHttpsCallable("createUser")
                .call(data)
                .await()

            val result = httpsCallableResult.data as Map<*, *>

            when (result["result"]) {
                WEAK_PASSWORD -> SignUpResult.WEAK_PASSWORD
                USERNAME_TAKEN -> SignUpResult.USERNAME_TAKEN
                EMAIL_ALREADY_EXISTS -> SignUpResult.EMAIL_ALREADY_REGISTERED
                SERVICE_UNAVAILABLE -> SignUpResult.SERVICE_UNAVAILABLE
                REQUEST_FAILED -> SignUpResult.FAIL
                else -> SignUpResult.SUCCESS
            }
        } catch (e: FirebaseFunctionsException) {
            SignUpResult.FAIL
        }
    }
}
