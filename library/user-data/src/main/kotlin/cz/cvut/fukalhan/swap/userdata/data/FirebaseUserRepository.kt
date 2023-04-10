package cz.cvut.fukalhan.swap.userdata.data

import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.userdata.domain.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.UserProfile
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository : UserRepository {
    private val functions = Firebase.functions

    override suspend fun getUserProfileData(uid: String): Response<ResponseFlag, UserProfile> {
        val data = hashMapOf(
            "uid" to uid
        )

        return try {
            val httpsCallableResult = functions
                .getHttpsCallable("getUserProfile")
                .call(data)
                .await()

            val result = httpsCallableResult.data as Map<*, *>

            when (result["result"]) {
                USER_NOT_FOUND -> Response(false, ResponseFlag.FAIL)
                USER_DATA_FETCH_ERROR -> Response(false, ResponseFlag.FAIL)
                else -> {
                    val username = result["username"].toString()
                    val profilePicUrl = result["profilePicUrl"].toString()
                    val joinDate = result["joinDate"].toString()
                    Response(true, ResponseFlag.SUCCESS, UserProfile(profilePicUrl, username, joinDate))
                }
            }
        } catch (e: FirebaseFunctionsException) {
            Response(false, ResponseFlag.FAIL)
        }
    }
}
