package cz.cvut.fukalhan.design.system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SwapAppDimensions(
    val sidePadding: Dp,
    val smallSidePadding: Dp,
    val smallSpacer: Dp,
    val mediumSpacer: Dp,
    val largeSpacer: Dp,
    val smallRoundCorners: Dp,
    val roundCorners: Dp,
    val borderWidth: Dp,
    val elevation: Dp,
    val image: Dp,
    val bar: Dp,
    val bottomScreenPadding: Dp,
    val icon: Dp,
    val imageView: Dp
)

val swapAppDimensions = SwapAppDimensions(
    sidePadding = 10.dp,
    smallSidePadding = 5.dp,
    smallSpacer = 10.dp,
    mediumSpacer = 25.dp,
    largeSpacer = 40.dp,
    smallRoundCorners = 10.dp,
    roundCorners = 20.dp,
    borderWidth = 1.dp,
    elevation = 5.dp,
    image = 180.dp,
    bar = 50.dp,
    bottomScreenPadding = 40.dp,
    icon = 35.dp,
    imageView = 250.dp
)
