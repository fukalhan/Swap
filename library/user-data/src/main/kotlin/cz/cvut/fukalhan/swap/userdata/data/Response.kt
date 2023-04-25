package cz.cvut.fukalhan.swap.userdata.data

data class Response<S>(
    val flag: S
)

class DataResponse<S, T>(
    val flag: S,
    val data: T? = null
)

enum class ResponseFlag {
    SUCCESS, DATA_NOT_FOUND, STORAGE_ERROR, DB_ERROR, FAIL
}
