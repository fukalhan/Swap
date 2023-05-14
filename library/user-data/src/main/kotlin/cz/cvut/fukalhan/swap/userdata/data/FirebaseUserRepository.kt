package cz.cvut.fukalhan.swap.userdata.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.Notification
import cz.cvut.fukalhan.swap.userdata.model.Review
import cz.cvut.fukalhan.swap.userdata.model.User
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository : UserRepository {
    private val storage = Firebase.storage
    private val db = Firebase.firestore

    override suspend fun getUserProfileData(userId: String): DataResponse<User> {
        return try {
            val docSnapshot = db.collection(USERS).document(userId).get().await()
            if (docSnapshot.exists()) {
                val profilePic = docSnapshot.get(PROFILE_PIC) as String
                val user = docSnapshot.toObject(User::class.java)
                user?.profilePicUri = Uri.parse(profilePic)
                user?.let {
                    return DataResponse.Success(it)
                }
            }
            DataResponse.Error()
        } catch (e: FirebaseFirestoreException) {
            Log.e("getUserProfileData", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getUsersProfilePic(reviews: List<Review>): DataResponse<List<Review>> {
        return try {
            reviews.forEach { review ->
                val docSnapshot = db.collection(USERS).document(review.reviewerId).get().await()
                if (docSnapshot.exists()) {
                    review.reviewerProfilePic = Uri.parse(docSnapshot.get(PROFILE_PIC) as String)
                }
            }
            DataResponse.Success(reviews)
        } catch (e: StorageException) {
            Log.e("getUsersProfilePic", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun changeUserProfilePicture(
        userId: String,
        profilePic: Uri
    ): Response {
        return try {
            val imageRef = storage.reference.child(PROFILE_IMAGES_FOLDER + userId)
            val uploadTask = imageRef.putFile(profilePic)

            val downloadTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }
            val downloadUri = downloadTask.await()

            val userRef = db.collection(USERS).document(userId)
            userRef.update(PROFILE_PIC, downloadUri)

            Response.Success
        } catch (e: StorageException) {
            Log.e("changeUserProfilePicture", "Exception $e")
            Response.Error
        }
    }

    override suspend fun updateUserBio(userId: String, bio: String): Response {
        return try {
            val userRef = db.collection(USERS).document(userId)
            userRef.update(BIO, bio).await()

            Response.Success
        } catch (e: FirebaseFirestoreException) {
            Log.e("updateUserBio", "Exception $e")
            Response.Error
        }
    }

    override suspend fun getNotificationData(
        userId: String,
        itemId: String
    ): DataResponse<Notification> {
        return try {
            val userDoc = db.collection(USERS).document(userId).get().await()
            val itemDoc = db.collection(ITEMS).document(itemId).get().await()
            if (userDoc.exists() && itemDoc.exists()) {
                val userProfilePic = Uri.parse(userDoc.getString(PROFILE_PIC) ?: EMPTY_FIELD)
                val username = userDoc.getString(USERNAME) ?: EMPTY_FIELD
                val itemName = itemDoc.getString(NAME) ?: EMPTY_FIELD

                DataResponse.Success(Notification(userId, userProfilePic, username, itemId, itemName))
            } else {
                DataResponse.Error()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("getNotificationData", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getUsersById(userIds: List<String>): DataResponse<List<User>> {
        return try {
            if (userIds.isEmpty()) {
                return DataResponse.Success(emptyList())
            }

            val querySnapshot = db.collection(USERS).whereIn(ID, userIds).get().await()
            val users = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.mapNotNull { doc ->
                    val profilePic = doc.get(PROFILE_PIC) as String
                    val user = doc.toObject(User::class.java)
                    user?.profilePicUri = Uri.parse(profilePic)
                    user
                }
            }
            DataResponse.Success(users)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getUsersById", "Exception $e")
            DataResponse.Error()
        }
    }
}
