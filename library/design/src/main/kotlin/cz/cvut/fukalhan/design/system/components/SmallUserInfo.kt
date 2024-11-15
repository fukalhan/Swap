package cz.cvut.fukalhan.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.model.SmallUserInfoVo
import cz.cvut.fukalhan.design.system.model.UserProfilePictureVo
import cz.cvut.fukalhan.design.theme.SwapAppTheme

@Composable
fun SmallUserInfo(
    model: SmallUserInfoVo,
    modifier: Modifier = Modifier,
    onClick: ((String) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = onClick != null,
            ) {
                onClick?.invoke(model.userId)
            }
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfilePicture(
            model = UserProfilePictureVo(
                uri = model.profilePicUri,
                size = 40.dp
            )
        )

        Text(
            text = model.username,
            style = SwapAppTheme.typography.titleSecondary,
        )
    }
}