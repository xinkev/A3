package feature.settings.backup.common.serilization

import kotlinx.serialization.json.Json


val taiyakiJson: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}
