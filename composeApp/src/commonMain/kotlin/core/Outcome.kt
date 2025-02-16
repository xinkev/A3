package core

sealed class Outcome<out ErrorType, out T> {
    class Error<out ErrorType>(val value: ErrorType) : Outcome<ErrorType, Nothing>()
    class Success<T>(val value: T) : Outcome<Nothing, T>()
}

inline fun <ErrorType, T> Outcome<ErrorType, T>.onError(action: (ErrorType) -> Unit) {
    if (this is Outcome.Error<ErrorType>) {
        action.invoke(this.value)
    }
}

inline fun <ErrorType, T> Outcome<ErrorType, T>.onSuccess(action: (T) -> Unit) {
    if (this is Outcome.Success<T>) {
        action.invoke(this.value)
    }
}
