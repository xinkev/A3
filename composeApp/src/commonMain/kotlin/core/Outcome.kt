package core

sealed class Outcome<out ErrorType, out T> {
    class Error<ErrorType>(val value: ErrorType) : Outcome<ErrorType, Nothing>()
    class Success<T>(val value: T) : Outcome<Nothing, T>()

    fun doOn(
        error: (ErrorType) -> Unit = {},
        success: (T) -> Unit = {}
    ) {
        when (this) {
            is Error -> error(value)
            is Success -> success(value)
        }
    }
}

