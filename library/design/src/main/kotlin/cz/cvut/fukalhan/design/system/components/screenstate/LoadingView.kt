package cz.cvut.fukalhan.design.system.components.screenstate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.theme.SwapAppTheme

@Composable
fun LoadingView(backgroundColor: Color = Color.Transparent) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(SwapAppTheme.dimensions.icon),
            color = SwapAppTheme.colors.primary
        )
    }
}
