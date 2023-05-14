package cz.cvut.fukalhan.swap.placesdata.data

sealed class Response<T> {
    class Success<T>(val data: T) : Response<T>()
    class Error<T> : Response<T>()
}

fun <T> Response<T>.resolve(
    onSuccess: (T) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        is Response.Success -> onSuccess(this.data)
        is Response.Error -> onError()
    }
}
