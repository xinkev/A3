package util

import co.touchlab.kermit.Logger

object log {
    const val TAG = "A3"

    inline fun i(message: () -> Any) {
        Logger.i(null, TAG) {
            message().toString()
        }
    }

    inline fun e(throwable: Throwable? = null, message: () -> Any) {
        Logger.e(throwable, TAG) {
            message().toString()
        }
    }
}