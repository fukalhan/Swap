package cz.cvut.fukalhan.design.system.model

import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * View object for clickable row component
 *
 * @property label main row text
 * @property onClick on the row click callback
 * @property startIcon optional icon at the beginning of the row
 * @property endIconVo optional icon at the end of the row
 * @property enabled determine if the row is enabled
 */
data class SelectRowVo(
    val label: StringModel,
    val onClick: () -> Unit,
    val startIcon: IconVo? = null,
    val endIconVo: IconVo? = null,
    val enabled: Boolean = true
)