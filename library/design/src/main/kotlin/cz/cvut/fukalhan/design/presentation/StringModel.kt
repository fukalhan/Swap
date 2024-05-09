package cz.cvut.fukalhan.design.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlin.String as KotlinString

/**
 * Model for storing information about string type and its value
 */
sealed interface StringModel {

    /**
     * String stored as a string value
     *
     * @param value value stored as a string
     */
    data class String(val value: KotlinString): StringModel

    /**
     * String stored as a resource
     *
     * @param id of the stored resource
     */
    data class Resource(@StringRes val id: Int): StringModel {
        /**
         * Array of variable number of values that needs to be provided to dynamic strings.
         */
        var params: Array<Any>? = null
            private set

        /**
         * Constructor with variable arguments to support dynamic strings.
         */
        constructor(id: Int, vararg params: Any) : this(id) {
            this.params = arrayOf(*params)
        }
    }

    /**
     * Empty string
     */
    data object Empty : StringModel

    /**
     * Retrieve string from StringModel in Composable screens.
     */
    @Composable
    fun getString(): KotlinString {
        return when(this) {
            is String -> value
            is Resource -> params?.map {
                if (it is StringModel) {
                    it.getString()
                } else {
                    it
                }
            }?.let {
                stringResource(
                    id = id,
                    formatArgs = it.toTypedArray()
                )
            } ?: stringResource(id = id)
            Empty -> ""
        }
    }
}