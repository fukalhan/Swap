package cz.cvut.fukalhan.design.system.model

import android.net.Uri
import androidx.compose.ui.layout.ContentScale
import cz.cvut.fukalhan.design.R

data class ItemImageVo(
    val uri: Uri,
    val contentScale: ContentScale,
    val placeholder: Int = R.drawable.item_placeholder
)
