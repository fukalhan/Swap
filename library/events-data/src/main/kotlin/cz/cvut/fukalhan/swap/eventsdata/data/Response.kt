package cz.cvut.fukalhan.swap.eventsdata.data

sealed class Response {
    object Success : Response()
    object Error : Response()
}

sealed class DataResponse<T>(val data: T? = null) {
    class Success<T>(data: T) : DataResponse<T>(data)
    class Error<T> : DataResponse<T>()
}
