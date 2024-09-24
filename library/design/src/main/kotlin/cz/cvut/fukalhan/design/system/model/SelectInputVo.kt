package cz.cvut.fukalhan.design.system.model

import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.components.SelectInput

/**
 * View object for [SelectInput] component
 *
 * @param value the displayed selected value
 * @param label optional label description on the top of the component
 * @param placeholder the value to be displayed in the input if the [value] is empty or blank
 * @param endIcon icon to be displayed at the end of the component
 * @param enabled determine if the component is enabled
 */
data class SelectInputVo(
    val value: String = "",
    val label: StringModel? = null,
    val placeholder: StringModel,
    val endIcon: IconVo? = null,
    val enabled: Boolean = true
)