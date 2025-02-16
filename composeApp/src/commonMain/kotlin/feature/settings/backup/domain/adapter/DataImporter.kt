package feature.settings.backup.domain.adapter

import core.Outcome
import feature.settings.backup.domain.model.ImportError

interface DataImporter {
    val source: String
    val format: String
    suspend fun import(readData: suspend () -> ByteArray): Outcome<ImportError, Unit>
}
