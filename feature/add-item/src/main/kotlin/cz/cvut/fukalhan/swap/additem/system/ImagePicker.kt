package cz.cvut.fukalhan.swap.additem.system

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.R

/**
 * Component for image picking
 *
 * @param selectedImages uris of selected images
 * @param maxImages limit of images to be picked
 * @param onSelectImages callback on images select
 * @param onRemoveImage callback on remove image
 */
@Composable
fun ImagePicker(
    selectedImages: List<Uri>,
    maxImages: Int,
    onSelectImages: (List<Uri>) -> Unit,
    onRemoveImage: (Uri) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(SwapAppTheme.dimensions.imageView)
            .padding(SwapAppTheme.dimensions.smallSidePadding),
        horizontalAlignment = Alignment.Start,
    ) {
        val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(
                maxItems = maxImages
            ),
            onResult = {
                onSelectImages(it)
            }
        )

        LazyRow {
            items(selectedImages) { uri ->
                ImageView(uri) {
                    onRemoveImage(it)
                }
            }
        }

        InstructionRow(
            modifier = Modifier.weight(1f),
            imagesCount = selectedImages.size,
            maxImages = maxImages
        ) {
            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}

@Composable
fun ImageView(
    uri: Uri,
    onRemoveClick: (Uri) -> Unit
) {
    Box(
        modifier = Modifier.size(SwapAppTheme.dimensions.image)
    ) {
        ItemImage(uri)

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(5.dp),
            onClick = { onRemoveClick(uri) }
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = SwapAppTheme.colors.onSurface),
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = null,
                tint = SwapAppTheme.colors.surface
            )
        }
    }
}

@Composable
fun ItemImage(uri: Uri) {
    AsyncImage(
        model = uri,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .border(
                width = SwapAppTheme.dimensions.borderWidth,
                color = SwapAppTheme.colors.onSurface,
                shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
            )
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun InstructionRow(
    modifier: Modifier = Modifier,
    imagesCount:Int,
    maxImages: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null,
            modifier = Modifier.size(SwapAppTheme.dimensions.icon)
        )

        Spacer(modifier = Modifier.size(SwapAppTheme.dimensions.smallSpacer))

        Text(
            text = stringResource(R.string.addImage),
            style = SwapAppTheme.typography.titlePrimary,
            color = SwapAppTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.padding(end = 10.dp),
            text = "$imagesCount/$maxImages",
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.onSurface
        )
    }
}
