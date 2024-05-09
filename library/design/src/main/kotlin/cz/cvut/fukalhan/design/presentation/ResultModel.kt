package cz.cvut.fukalhan.design.presentation

/**
 * Interface to distinguish different kinds of results on screens
 *
 * @property message message to be displayed in the result view
 */
interface ResultModel {

    val message: StringModel

    /**
     * Success result
     */
    data class Success(override val message: StringModel) : ResultModel

    /**
     * Error result
     */
    data class Error(override val message: StringModel) : ResultModel

    /**
     * Warning result
     */
    data class Warning(override val message: StringModel) : ResultModel
}