package cz.cvut.fukalhan.swap.userdata.data

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import cz.cvut.fukalhan.swap.userdata.domain.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.UserProfile
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository : UserRepository {
    private val storage = Firebase.storage
    private val db = Firebase.firestore

    override suspend fun getUserProfileData(uid: String): Response<ResponseFlag, UserProfile> {
        try {
            val profilePicRef = storage.getReference("usersProfileImages/$uid")

            val profilePicExists = try {
                profilePicRef.metadata.await()
                true
            } catch (e: Exception) {
                false
            }

            val imageUri = if (profilePicExists) {
                profilePicRef.downloadUrl.await()
            } else {
                val defaultProfilePicRef = storage.getReference("usersProfileImages/profilePicPlaceholder.png")
                defaultProfilePicRef.downloadUrl.await()
            }

            val userRef = db.collection("Users").document(uid)
            val userDoc = userRef.get().await()

            return if (userDoc.exists()) {
                val userProfile = UserProfile(
                    imageUri,
                    userDoc.getString("username") ?: "",
                    userDoc.getString("joinDate") ?: ""
                )
                Response(true, ResponseFlag.SUCCESS, userProfile)
            } else {
                Response(false, ResponseFlag.DATA_NOT_FOUND, null)
            }
        } catch (e: StorageException) {
            return Response(false, ResponseFlag.STORAGE_ERROR, null)
        } catch (e: FirebaseFirestoreException) {
            return Response(false, ResponseFlag.DB_ERROR, null)
        }
    }
}
