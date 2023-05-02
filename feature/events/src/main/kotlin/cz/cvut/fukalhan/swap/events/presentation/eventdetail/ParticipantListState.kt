package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import android.net.Uri
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class ParticipantListState {

    object Init : ParticipantListState()

    object Loading : ParticipantListState()

    data class Success(
        val participantsCount: String,
        val participants: List<ParticipantInfo>
    ) : ParticipantListState()

    data class Empty(val message: Int = R.string.emptyParticipantList) : ParticipantListState()

    data class Failure(val message: Int = R.string.cannotLoadParticipantList) : ParticipantListState()
}

data class ParticipantInfo(
    val id: String,
    val profilePic: Uri,
    val username: String,
)

internal fun User.toParticipantInfo(): ParticipantInfo {
    return ParticipantInfo(
        this.id,
        this.profilePicUri,
        this.username
    )
}
