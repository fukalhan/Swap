package cz.cvut.fukalhan.swap.auth.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.auth.domain.Repository
import cz.cvut.fukalhan.swap.auth.model.SignUpCredentials
import kotlinx.coroutines.tasks.await

class FirebaseRepository : Repository {
    private val auth = Firebase.auth

    override suspend fun signUpUser(signUpCredentials: SignUpCredentials) {
        val credentials = auth
            .createUserWithEmailAndPassword(
                signUpCredentials.email,
                signUpCredentials.password
            ).await()
        val profileChangeRequest = userProfileChangeRequest {
            displayName = signUpCredentials.username
        }
        credentials.user?.updateProfile(profileChangeRequest)?.await()
    }
}
