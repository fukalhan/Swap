package cz.cvut.fukalhan.swap.swapsearch.system

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cz.cvut.fukalhan.swapsearch.R
import cz.cvut.fukalhan.swap.swapsearch.presentation.ItemState

@Composable
fun ItemCard(itemState: ItemState) {
    Column {
        //Item image
        ItemDescription(itemState.name, itemState.location)
    }
}

@Composable
fun ItemDescription(name: String, location: String) {
    Row {
        Text(
            text = name
        )

        Icon(
            painter = painterResource(R.drawable.location),
            contentDescription = "Location icon"
        )

        Text(
            text = location
        )
    }
}

