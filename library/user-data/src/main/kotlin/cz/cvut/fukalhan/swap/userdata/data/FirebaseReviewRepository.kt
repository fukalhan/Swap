package cz.cvut.fukalhan.swap.userdata.data

import android.net.Uri
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.model.Review
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseReviewRepository : ReviewRepository {
    private val db = Firebase.firestore

    override suspend fun addReview(
        review: Review
    ): Response<ResponseFlag> {
        return try {
            val docRef = db.collection(REVIEWS).document()
            review.id = docRef.id

            db.collection(REVIEWS).document(review.id).set(review).await()
            Response(ResponseFlag.SUCCESS)
        } catch (e: Exception) {
            Response(ResponseFlag.FAIL)
        }
    }

    override suspend fun getUserRating(userId: String): DataResponse<ResponseFlag, Float> {
        return try {
            val querySnapshot = db.collection(REVIEWS).whereEqualTo(USER_ID, userId).get().await()
            val size = querySnapshot.size().toFloat()
            var cumulativeRating = 0f
            querySnapshot.documents.forEach { doc ->
                cumulativeRating += (doc.getLong(RATING) ?: 0).toInt()
            }
            if (size != 0f) {
                DataResponse(ResponseFlag.SUCCESS, cumulativeRating / size)
            } else {
                DataResponse(ResponseFlag.SUCCESS, 0f)
            }
        } catch (e: Exception) {
            DataResponse(ResponseFlag.FAIL)
        }
    }

    override suspend fun getUserReviews(userId: String): DataResponse<ResponseFlag, List<Review>> {
        return try {
            val querySnapshot = db.collection(REVIEWS).whereEqualTo(USER_ID, userId).get().await()
            val reviews = if (querySnapshot.documents.isEmpty()) {
                emptyList()
            } else {
                querySnapshot.documents.mapNotNull { doc ->
                    mapDocToReview(doc)
                }
            }

            DataResponse(ResponseFlag.SUCCESS, reviews)
        } catch (e: Exception) {
            DataResponse(ResponseFlag.FAIL)
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
