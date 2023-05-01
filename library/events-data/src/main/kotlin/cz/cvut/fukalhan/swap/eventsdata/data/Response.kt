package cz.cvut.fukalhan.swap.eventsdata.data

data class Response<S>(val success: Boolean, val flag: S)

data class DataResponse<S, T>(val success: Boolean, val flag: S, val data: T? = null)

enum class ResponseFlag {
    SUCCESS, FAIL
}
