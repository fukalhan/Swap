package cz.cvut.fukalhan.design.system.model

import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * Interface for various buttons
 */
interface ButtonVo {

    /**
     * Basic button view object with text and optional icons
     *
     * @property label button text label
     * @property onClick on button click callback
     * @property startIcon optional start icon
     * @property endIcon optional end icon
     * @property enabled determine if the button is enabled
     */
    data class Basic(
        val label: StringModel,
        val onClick: () -> Unit,
        val startIcon: IconVo? = null,
        val endIcon: IconVo? = null,
        val enabled: Boolean = true
    )
}