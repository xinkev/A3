package feature.settings.backup.serilization

import kotlinx.serialization.json.Json


val taiyakiJson: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}
