package cz.cvut.fukalhan.swap.itemdetail.system

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.components.ImagePager
import cz.cvut.fukalhan.design.system.components.ItemStateView
import cz.cvut.fukalhan.design.system.model.ImagePagerVo
import cz.cvut.fukalhan.swap.itemdata.model.State

@Composable
fun ImageView(
    images: List<Uri>,
    itemState: State,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clickable { onClick() }
    ) {
        ItemStateView(
            itemState == State.RESERVED || itemState == State.SWAPPED,
            itemState.label,
            Modifier.align(Alignment.BottomCenter)
        )
        ImagePager(
            imagePagerVo = ImagePagerVo(
                images = images,
                isFullSizeView = false
            )
        )
    }
}
