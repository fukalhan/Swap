package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.model.CarouselVo

@Composable
fun Carousel(
    carouselVo: CarouselVo,
    modifier: Modifier
) {
    if (carouselVo.size > 1) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(carouselVo.size) { iteration ->
                val color = if (carouselVo.currentPage == iteration) Color.LightGray else Color.DarkGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
            }
        }
    }
}

@Composable
@Preview
fun CarouselPreview() {
    Carousel(
        carouselVo = CarouselVo(
            size = 3,
            currentPage = 1
        ),
        modifier = Modifier
    )
}
