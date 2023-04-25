package cz.cvut.fukalhan.swap.userdata.data

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
}
