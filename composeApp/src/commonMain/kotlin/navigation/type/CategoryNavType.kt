package navigation.type

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import common.util.decoded
import common.util.encoded
import feature.category.common.domain.model.Category
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

val CategoryNavType = object : NavType<Category?>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Category? {
        val json = bundle.getString(key) ?: return null
        return Json.decodeFromString<Category>(json)
    }

    override fun parseValue(value: String): Category? {
        if (value.isEmpty()) return null
        return Json.decodeFromString<Category>(value.decoded())
    }

    override fun put(bundle: Bundle, key: String, value: Category?) {
        if (value == null) return
        val json = Json.encodeToString(value)
        bundle.putString(key, json)
    }

    override fun serializeAsValue(value: Category?): String {
        if (value == null) return ""

        return Json.encodeToString(value).encoded()
    }
}

val categoryNavTypeMap = mapOf(typeOf<Category?>() to CategoryNavType)
