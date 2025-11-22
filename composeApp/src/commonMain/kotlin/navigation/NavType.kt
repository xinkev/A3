package navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import common.util.decoded
import common.util.encoded
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

inline fun <reified T> navType(): NavType<T> {
    val isNullable = null is T
    return object : NavType<T>(isNullableAllowed = isNullable) {

        override fun put(bundle: SavedState, key: String, value: T) {
            if (!isNullable && value == null) {
                throw SerializationException("null is not allowed for non-nullable type: $name")
            } else if (value == null) {
                return
            }
            bundle.write {
                putString(key, serializeAsValue(value))
            }
        }

        override fun get(bundle: SavedState, key: String): T {
            val (containsKey, value) = bundle.read {
                contains(key) to getStringOrNull(key)
            }
            return if (value == null) {
                if (isNullable) {
                    value as T
                }
                if (containsKey) {
                    throw SerializationException("null value for non-nullable NavType: $name")
                } else {
                    throw SerializationException("Key $key not found for NavType: $name")
                }
            } else {
                Json.decodeFromString(value.decoded())
            }
        }

        override fun parseValue(value: String): T {
            return if (value.isEmpty() && isNullable) {
                return null as T
            } else {
                Json.decodeFromString<T>(value.decoded())
            }
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
