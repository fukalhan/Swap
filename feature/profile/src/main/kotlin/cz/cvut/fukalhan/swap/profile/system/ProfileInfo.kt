package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R

@Composable
fun ProfileInfo() {
    Row(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.sidePadding)
            .clip(RoundedCornerShape(SwapAppTheme.dimensions.roundCorners))
            .background(SwapAppTheme.colors.componentBackground)
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ProfilePicture()
        Spacer(modifier = Modifier.width(SwapAppTheme.dimensions.mediumSpacer))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            UsernameView()
        }
    }
}

@Composable
fun ProfilePicture() {
    /*Image(
        painter = painterResource(R.drawable.profile),
        contentDescription = "Profile picture",
        modifier = Modifier
            .size(150.dp),
        contentScale = ContentScale.Fit
    )*/
    AsyncImage(
        model = null,
        placeholder = painterResource(R.drawable.profile),
        contentDescription = "Profile picture",
        modifier = Modifier
            .size(150.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun UsernameView() {
    Text(
        text = "Username",
        style = SwapAppTheme.typography.titleSecondary
    )
}
