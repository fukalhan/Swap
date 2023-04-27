package cz.cvut.fukalhan.swap.userdata.model

import android.net.Uri
import com.google.firebase.firestore.DocumentSnapshot
import cz.cvut.fukalhan.swap.userdata.data.REVIEWER_PROFILE_PIC
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

data class Review(
    var id: String = "",
    var userId: String = "",
    var reviewerId: String = "",
    @get:JvmName("getReviewerProfilePicUri")
    @set:JvmName("setReviewerProfilePicUri")
    var reviewerProfilePic: Uri = Uri.EMPTY,
    var rating: Int = 0,
    var description: String = ""
) {
    companion object {
        @JvmStatic
        @JvmName("fromSnapshot")
        @Throws(RuntimeException::class)
        fun fromSnapshot(snapshot: DocumentSnapshot): Review {
            return snapshot.toObject(Review::class.java)?.apply {
                val profilePicUri = snapshot.getString(REVIEWER_PROFILE_PIC)
                reviewerProfilePic = Uri.parse(profilePicUri)
            } ?: throw RuntimeException("Unable to deserialize Review object")
        }
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(reviewerProfilePic.toString())
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        val profilePicUriString = `in`.readObject() as String
        reviewerProfilePic = Uri.parse(profilePicUriString)
    }
}
