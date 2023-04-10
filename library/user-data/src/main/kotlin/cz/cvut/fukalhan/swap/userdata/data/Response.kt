package cz.cvut.fukalhan.swap.userdata.data

class Response<S, T>(
    val success: Boolean,
    val flag: S,
    val data: T? = null
)

enum class ResponseFlag {
    SUCCESS, FAIL
}
