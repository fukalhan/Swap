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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.R

const val PICTURES_LIMIT = 6

@Composable
fun PictureSelectionView(
    selectedImagesUri: List<Uri>,
    setSelectedImagesUri: (List<Uri>) -> Unit
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .padding(bottom = SwapAppTheme.dimensions.sidePadding)
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(),
                onResult = { uris ->
                    val prevImagesSize = selectedImagesUri.size
                    setSelectedImagesUri(
                        selectedImagesUri.take(PICTURES_LIMIT) + uris.take(PICTURES_LIMIT - prevImagesSize)
                    )
                }
            )

            LazyRow {
                items(selectedImagesUri) { uri ->
                    ImageItem(uri) {
                        setSelectedImagesUri(
                            selectedImagesUri.filter {
                                it != uri
                            }
                        )
                    }
                }
            }

            InstructionRow(selectedImagesUri, Modifier.weight(1f)) {
                multiplePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }
}

@Composable
fun ImageItem(
    uri: Uri,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(SwapAppTheme.dimensions.image)
    ) {
        ItemImage(uri)
        CancelIcon(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onCancelClick
        )
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
                color = SwapAppTheme.colors.component,
                shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)
            )
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CancelIcon(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(R.drawable.cancel),
        contentDescription = null,
        tint = SwapAppTheme.colors.backgroundSecondary,
        modifier = modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .clip(CircleShape)
            .background(color = SwapAppTheme.colors.component)
            .clickable(onClick = onClick)
    )
}

@Composable
fun InstructionRow(
    selectedImagesUri: List<Uri>,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = null,
            tint = SwapAppTheme.colors.textSecondary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.size(SwapAppTheme.dimensions.smallSpacer))
        Text(
            text = stringResource(R.string.addImage),
            style = SwapAppTheme.typography.titlePrimary,
            color = SwapAppTheme.colors.textSecondary
        )

        Spacer(modifier = Modifier.size(SwapAppTheme.dimensions.largeSpacer))
        Text(
            text = "${selectedImagesUri.size}/$PICTURES_LIMIT",
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textSecondary
        )
    }
}
