package cz.cvut.fukalhan.swap.auth.data

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.auth.domain.Repository
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import cz.cvut.fukalhan.swap.auth.model.SignUpResponse
import kotlinx.coroutines.tasks.await

class FirebaseRepository : Repository {
    private val auth = Firebase.auth

    override suspend fun signUpUser(signUpCredentials: SignUpCredentials): SignUpResponse {
        return try {
            /*val profileChangeRequest = userProfileChangeRequest {
                displayName = signUpCredentials.username
            }*/

            auth
                .createUserWithEmailAndPassword(
                    signUpCredentials.email,
                    signUpCredentials.password
                ).await()
            SignUpResponse.SUCCESS
        } catch (e: FirebaseAuthException) {
            return when (e) {
                is FirebaseAuthWeakPasswordException -> SignUpResponse.WEAK_PASSWORD
                is FirebaseAuthUserCollisionException -> SignUpResponse.EMAIL_ALREADY_REGISTERED
                else -> SignUpResponse.FAIL
            }
        }
    }
}
