package cz.cvut.fukalhan.swap.events.model.eventdetail

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringModel

data class OrganizerInfoVo(
    val id: String,
    val profilePic: Uri,
    val username: String,
    val joinDate: StringModel,
    val rating: Float
)