package cz.cvut.fukalhan.swap.itemdetail.view

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.system.components.ImagePager
import cz.cvut.fukalhan.design.system.components.ItemStateView
import cz.cvut.fukalhan.design.system.model.ImagePagerVo
import cz.cvut.fukalhan.swap.itemdata.model.State

/**
 * Image view to display item image in horizontal pager
 * and the item state
 *
 * @param modifier modifier to be applied to the outer layout
 * @param images images to be displayed in the pager
 * @param itemState item state
 * @param onClick on image click action
 */
@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    images: List<Uri>,
    itemState: State,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        ImagePager(
            imagePagerVo = ImagePagerVo(
                images = images,
                isFullSizeView = false
            )
        )

        if (itemState == State.RESERVED || itemState == State.SWAPPED) {
            ItemStateView(
                modifier = Modifier.align(Alignment.BottomCenter),
                label = itemState.label
            )
        }
    }
}
