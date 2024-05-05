package feature.backup

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import util.parseTaiYakiDateTime

class ExpenseTaiyakiBackupAdapter : BackupAdapter {
    private val json: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, TaiyakiLocalDateTimeCustomSerializer)
        }
    }

    override suspend fun import(json: String): Backup {
        return this.json.decodeFromString<Backup>(json)
    }
}


object TaiyakiLocalDateTimeCustomSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime =
        parseTaiYakiDateTime(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

}