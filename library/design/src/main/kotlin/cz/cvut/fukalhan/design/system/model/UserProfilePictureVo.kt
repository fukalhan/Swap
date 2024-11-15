package cz.cvut.fukalhan.design.system.model

import android.net.Uri
import androidx.compose.ui.unit.Dp

data class UserProfilePictureVo(
    val uri: Uri,
    val size: Dp
)