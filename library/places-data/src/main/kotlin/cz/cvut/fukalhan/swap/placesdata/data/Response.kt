package cz.cvut.fukalhan.swap.placesdata.data

sealed class Response<T> {
    class Success<T>(val data: T) : Response<T>()
    class Error<T> : Response<T>()
}
