package cz.cvut.fukalhan.swap.userdata.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.Review
import cz.cvut.fukalhan.swap.userdata.model.User
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository : UserRepository {
    private val storage = Firebase.storage
    private val db = Firebase.firestore

    override suspend fun getUserProfileData(userId: String): DataResponse<ResponseFlag, User> {
        try {
            val docSnapshot = db.collection(USERS).document(userId).get().await()
            return if (docSnapshot.exists()) {
                val profilePic = docSnapshot.get(PROFILE_PIC) as String
                val user = docSnapshot.toObject(User::class.java)
                user?.profilePicUri = Uri.parse(profilePic)

                DataResponse(ResponseFlag.SUCCESS, user)
            } else {
                DataResponse(ResponseFlag.DATA_NOT_FOUND, null)
            }
        } catch (e: StorageException) {
            return DataResponse(ResponseFlag.STORAGE_ERROR, null)
        } catch (e: FirebaseFirestoreException) {
            return DataResponse(ResponseFlag.DB_ERROR, null)
        }
    }

    override suspend fun getUsersProfilePic(reviews: List<Review>): DataResponse<ResponseFlag, List<Review>> {
        return try {
            reviews.forEach { review ->
                val docSnapshot = db.collection(USERS).document(review.reviewerId).get().await()
                if (docSnapshot.exists()) {
                    review.reviewerProfilePic = Uri.parse(docSnapshot.get(PROFILE_PIC) as String)
                }
            }
            DataResponse(ResponseFlag.SUCCESS, reviews)
        } catch (e: StorageException) {
            DataResponse(ResponseFlag.FAIL)
        }
    }

    override suspend fun changeUserProfilePicture(
        userId: String,
        profilePic: Uri
    ): Response<ResponseFlag> {
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

            Response(ResponseFlag.SUCCESS)
        } catch (e: StorageException) {
            Response(ResponseFlag.SUCCESS)
        }
    }

    override suspend fun updateUserBio(userId: String, bio: String): Response<ResponseFlag> {
        return try {
            val userRef = db.collection(USERS).document(userId)
            userRef.update(BIO, bio).await()

            Response(ResponseFlag.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(ResponseFlag.FAIL)
        }
    }
}
