package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class OrganizerInfoState {
    object Init : OrganizerInfoState()
    object Loading : OrganizerInfoState()
    data class Success(
        val organizer: OrganizerInfo
    ) : OrganizerInfoState()

    data class Failure(val message: Int = R.string.cannotLoadOrganizerInfo) : OrganizerInfoState()
}

data class OrganizerInfo(
    val id: String,
    val profilePic: Uri,
    val username: String,
    val joinDate: String,
    val rating: Float
)

internal fun User.toOrganizerInfoState(stringResources: StringResources): OrganizerInfoState {
    return OrganizerInfoState.Success(
        OrganizerInfo(
            this.id,
            this.profilePicUri,
            this.username,
            stringResources.getString(R.string.memberSince, formatDate(this.joinDate)),
            this.rating
        )
    )
}
