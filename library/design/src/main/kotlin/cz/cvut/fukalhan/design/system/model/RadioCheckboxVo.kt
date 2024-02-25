package cz.cvut.fukalhan.design.system.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

/**
 * Model for the RadioCheckbox component.
 *
 * @param isSelected determine if the checkbox is selected
 * @param selectedIcon icon to be displayed when the checkbox is selected
 * @param selectedTint tinto of the selected icon border if the checkbox is selected
 */
data class RadioCheckboxVo(
    val isSelected: Boolean = false,
    @DrawableRes
    val selectedIcon: Int,
    val selectedTint: Color
)