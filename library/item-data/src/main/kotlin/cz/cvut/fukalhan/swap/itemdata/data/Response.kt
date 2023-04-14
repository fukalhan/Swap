package cz.cvut.fukalhan.swap.itemdata.data

data class Response<T>(val success: Boolean, val data: T? = null)

enum class SaveItemResponse {
    SUCCESS, FAIL
}