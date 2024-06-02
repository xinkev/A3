package core

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID.UUID().UUIDString