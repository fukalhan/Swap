package cz.cvut.fukalhan.design.system.model

import cz.cvut.fukalhan.design.presentation.StringModel

data class ListBottomSheetVo(
    val title: StringModel,
    val items: List<RadioCheckboxRowVo>
)