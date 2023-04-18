package cz.cvut.fukalhan.design.system

import android.content.Context
import cz.cvut.fukalhan.design.presentation.StringResources

class AndroidStringResources(
    private val context: Context
) : StringResources {
    override fun getString(stringId: Int): String {
        return context.resources.getString(stringId)
    }

    override fun getString(stringId: Int, vararg formatArgs: Any): String {
        return context.resources.getString(stringId, *formatArgs)
    }
}
