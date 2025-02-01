package feature.backup.data

import common.util.A3DateFormat
import common.util.parseDateTime
import feature.backup.domain.adapter.RestoreAdapter
import feature.backup.domain.models.Backup
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

private object TaiyakiLocalDateTimeCustomSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime =
        parseDateTime(decoder.decodeString(), format = A3DateFormat.DisplayDateTime)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

}

class ExpenseTaiyakiRestoreAdapter : RestoreAdapter {
    private val json: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, TaiyakiLocalDateTimeCustomSerializer)
        }
    }

    override suspend fun restore(json: String): Backup {
        return this.json.decodeFromString<Backup>(json)
    }
}
