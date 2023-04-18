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
    val button: TextStyle
)

val swapAppTypography = SwapAppTypography(
    screenTitle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    titlePrimary = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        letterSpacing = 0.15.sp
    ),
    titleSecondary = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    body = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.15.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = 0.15.sp,
    )
)
