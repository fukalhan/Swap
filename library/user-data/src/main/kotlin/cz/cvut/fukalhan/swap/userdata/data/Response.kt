package cz.cvut.fukalhan.swap.userdata.data

sealed class Response {
    object Success : Response()

    object Error : Response()
}

fun Response.resolve(
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    when (this) {
        is Response.Success -> onSuccess()
        is Response.Error -> onError()
    }
}

sealed class DataResponse<T> {

    data class Success<T>(val data: T) : DataResponse<T>()

    class Error<T> : DataResponse<T>()
}

fun <T> DataResponse<T>.resolve(
    onSuccess: (T) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        is DataResponse.Success -> onSuccess(this.data)
        is DataResponse.Error -> onError()
    }
}
