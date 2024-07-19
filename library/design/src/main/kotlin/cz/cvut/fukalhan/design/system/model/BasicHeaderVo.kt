package cz.cvut.fukalhan.design.system.model

import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.theme.SwapAppTheme


/**
 * View object for the BasicHeader component
 *
 * @property title header main title
 * @property onBackClick on back button click callback,
 * if null then the back button is not displayed
 * @property endIcons list of optional end icons
 * @property backButton back button model
 */
data class BasicHeaderVo(
    val title: StringModel? = null,
    val onBackClick: (() -> Unit)? = null,
    val endIcons: List<IconButtonVo>? = null
) {
    val backButton = onBackClick?.let {
        IconButtonVo(
            iconVo = IconVo(
                res = R.drawable.ic_arrow_back,
                tint = SwapAppTheme.colors.onPrimary
            ),
            onClick = it
        )
    }
}