package cz.cvut.fukalhan.design.system.model

import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * Model for the CircleCheckboxRow component
 *
 * @param id id of the selected item
 * @param title text to be displayed in the row
 * @param isSelected determine if the current item is selected
 */
data class RadioCheckboxRowVo(
    val id: Long,
    val title: StringModel,
    val isSelected: Boolean = false
)