package navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import common.util.decoded
import common.util.encoded
import kotlinx.serialization.json.Json

inline fun <reified T> navType(): NavType<T> {
    val isNullable = null is T
    return object : NavType<T>(isNullableAllowed = isNullable) {
        override fun get(bundle: Bundle, key: String): T? {
            val json = bundle.getString(key) ?: return null
            return Json.decodeFromString(json)
        }

        override fun parseValue(value: String): T {
            return if (value.isEmpty() && isNullable) {
                return null as T
            } else {
                Json.decodeFromString<T>(value.decoded())
            }
        }

        override fun put(bundle: Bundle, key: String, value: T) {
            if (!isNullable && value == null) {
                throw IllegalArgumentException("null is not allowed for non-nullable type")
            } else if (value == null) {
                return
            }
            val json = Json.encodeToString(value)
            bundle.putString(key, json)
        }

        override fun serializeAsValue(value: T): String {
            if (!isNullable && value == null) {
                throw IllegalArgumentException("null is not allowed for non-nullable type")
            } else if (value == null) {
                return ""
            }
            return Json.encodeToString(value).encoded()
        }
    }
}
