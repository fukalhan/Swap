package cz.cvut.fukalhan.design.system

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class SwapAppTypography(
    val screenTitle: TextStyle,
    val titlePrimary: TextStyle,
    val titleSecondary: TextStyle,
    val body: TextStyle,
    val button: TextStyle,
    val smallText: TextStyle
)

val swapAppTypography = SwapAppTypography(
    screenTitle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        letterSpacing = 0.sp,
        color = white
    ),
    titlePrimary = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        letterSpacing = 0.15.sp,
        color = darkGrey
    ),
    titleSecondary = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        color = darkGrey
    ),
    body = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.15.sp,
        color = grey
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = 0.15.sp,
        color = white
    ),
    smallText = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.15.sp,
    ),
)
