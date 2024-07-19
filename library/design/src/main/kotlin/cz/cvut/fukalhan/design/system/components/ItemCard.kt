package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.theme.SwapAppTheme

@Composable
fun ItemCard(
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = SwapAppTheme.colors.background
        ),
        modifier = Modifier
            .padding(6.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            content.invoke(this)
        }
    }
}
