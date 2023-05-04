package cz.cvut.fukalhan.swap.userdata.data

data class Response<S>(
    val flag: S
)

data class DataResponse<S, T>(
    val flag: S,
    val data: T? = null
)

enum class ResponseFlag {
    SUCCESS, FAIL
}
