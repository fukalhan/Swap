package cz.cvut.fukalhan.design.system.model

import android.net.Uri

data class ImagePagerVo(
    val images: List<Uri>,
    val isFullSizeView: Boolean
)
