package cz.cvut.fukalhan.swap.userdata.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.model.Review
import kotlinx.coroutines.tasks.await

class FirebaseReviewRepository : ReviewRepository {
    private val db = Firebase.firestore

    override suspend fun addReview(
        review: Review
    ): Response {
        return try {
            val docRef = db.collection(REVIEWS).document()
            review.id = docRef.id

            db.collection(REVIEWS).document(review.id).set(review).await()
            Response.Success
        } catch (e: FirebaseFirestoreException) {
            Log.e("addReview", "Exception $e")
            Response.Error
        }
    }

    override suspend fun getUserRating(userId: String): DataResponse<Float> {
        return try {
            val querySnapshot = db.collection(REVIEWS).whereEqualTo(USER_ID, userId).get().await()
            val size = querySnapshot.size().toFloat()
            var cumulativeRating = 0f
            querySnapshot.documents.forEach { doc ->
                cumulativeRating += (doc.getLong(RATING) ?: 0).toInt()
            }
            if (size != 0f) {
                DataResponse.Success(cumulativeRating / size)
            } else {
                DataResponse.Success(0f)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("getUserRating", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getUserReviews(userId: String): DataResponse<List<Review>> {
        return try {
            val querySnapshot = db.collection(REVIEWS).whereEqualTo(USER_ID, userId).get().await()
            val reviews = if (querySnapshot.documents.isEmpty()) {
                emptyList()
            } else {
                querySnapshot.documents.mapNotNull { doc ->
                    mapDocToReview(doc)
                }
            }

            DataResponse.Success(reviews)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getUserReviews", "Exception $e")
            DataResponse.Error()
        }
    }
}

private fun mapDocToReview(doc: DocumentSnapshot): Review {
    return Review(
        doc.id,
        doc.getString(USER_ID) ?: EMPTY_FIELD,
        doc.getString(REVIEWER_ID) ?: EMPTY_FIELD,
        Uri.parse(doc.getString(REVIEWER_PROFILE_PIC)),
        doc.get(RATING) as? Int ?: 0,
        doc.getString(DESCRIPTION) ?: EMPTY_FIELD
    )
}
