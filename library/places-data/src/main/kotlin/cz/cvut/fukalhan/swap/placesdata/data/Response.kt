package cz.cvut.fukalhan.swap.placesdata.data

sealed class Response<T>(val data: T? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Error<T> : Response<T>()
}
