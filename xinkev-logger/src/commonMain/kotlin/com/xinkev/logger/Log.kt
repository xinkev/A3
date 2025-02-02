package com.xinkev.logger

import co.touchlab.kermit.Logger

@Suppress("ClassName")
object log {
    inline fun i(tag: String? = null, message: () -> Any) {
        Logger.i(null, tag ?: "") {
            message().toString()
        }
    }

    inline fun e(tag: String? = null, throwable: Throwable? = null, message: () -> Any) {
        Logger.e(throwable, tag ?: "") {
            message().toString()
        }
    }

    inline fun d(tag: String? = null, throwable: Throwable? = null, message: () -> Any) {
        Logger.d(throwable, tag ?: "") {
            message().toString()
        }
    }

    inline fun w(tag: String? = null, throwable: Throwable? = null, message: () -> Any) {
        Logger.w(throwable, tag ?: "") {
            message().toString()
        }
    }
}
