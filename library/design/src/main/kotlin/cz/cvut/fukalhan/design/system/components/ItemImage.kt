package cz.cvut.fukalhan.design.system.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.cvut.fukalhan.design.system.model.ItemImageVo

@Composable
fun ItemImage(
    itemImageVo: ItemImageVo,
    modifier: Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(itemImageVo.uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(itemImageVo.placeholder),
        contentDescription = null,
        modifier = modifier,
        contentScale = itemImageVo.contentScale
    )
}
