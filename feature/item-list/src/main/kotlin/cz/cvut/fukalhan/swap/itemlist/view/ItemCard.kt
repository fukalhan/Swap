package cz.cvut.fukalhan.swap.itemlist.view

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.components.IconButton
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ItemCard
import cz.cvut.fukalhan.design.system.components.ItemStateView
import cz.cvut.fukalhan.design.system.model.IconButtonVo
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.system.model.IconVo
import cz.cvut.fukalhan.swap.itemlist.model.ItemListScreenEvent
import cz.cvut.fukalhan.swap.itemlist.model.ItemVo

/**
 * Component to display item in a list
 */
@Composable
fun ItemCard(
    model: ItemVo,
    sendEvent: (ItemListScreenEvent) -> Unit,
) {
    ItemCard(
        onClick = {
            sendEvent(ItemListScreenEvent.OnItemClick(model.id))
        }
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (model.state == State.RESERVED || model.state == State.SWAPPED) {
                ItemStateView(
                    modifier = Modifier.align(Alignment.TopCenter),
                    label = model.state.label
                )
            }

            ItemPicture(
                uri = model.imageUri
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(5.dp),
                model = IconButtonVo(
                    iconVo = IconVo(
                        res = if (model.isLiked) R.drawable.colored_heart else R.drawable.heart,
                        size = 35.dp
                    ),
                    onClick = {
                        sendEvent(
                            ItemListScreenEvent.OnItemLikeClick(
                                id = model.id,
                                liked = !model.isLiked
                            )
                        )
                    }
                )
            )
        }

        Text(
            modifier = Modifier
                .padding(10.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            text = model.name,
            style = SwapAppTheme.typography.titleSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ItemPicture(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.item_placeholder),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
