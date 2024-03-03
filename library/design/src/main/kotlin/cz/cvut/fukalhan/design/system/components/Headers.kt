package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.R

@Composable
fun Header(
    title: String,
    leftIcon: Int? = null,
    rightIcon: Int? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftIcon?.let { icon ->
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.weight(4f),
            text = title,
            style = SwapAppTheme.typography.screenTitle,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        rightIcon?.let { icon ->
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun HeaderPreview() {
    Header(
        title = "Main Page",
        leftIcon = R.drawable.ic_arrow_back
    )
}