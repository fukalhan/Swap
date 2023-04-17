package cz.cvut.fukalhan.swap.itemdata.data

data class Response<S>(val success: Boolean, val flag: S)

data class DataResponse<S, T>(val success: Boolean, val flag: S, val data: T? = null)

enum class ResponseFlag {
    SUCCESS, FAIL
}

internal fun mapResponseFlag(flag: Int): ResponseFlag {
    return when (flag) {
        0 -> ResponseFlag.SUCCESS
        else -> ResponseFlag.FAIL
    }
}
