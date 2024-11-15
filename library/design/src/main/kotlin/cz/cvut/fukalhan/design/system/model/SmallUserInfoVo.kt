package cz.cvut.fukalhan.design.system.model

import android.net.Uri

data class SmallUserInfoVo(
    val userId: String,
    val profilePicUri: Uri,
    val username: String
)