package feature.settings.backup.domain.adapter

import core.Outcome
import feature.settings.backup.domain.model.ExportError
import io.github.vinceglb.filekit.core.PlatformDirectory

interface DataExporter {
    val source: String
    val format: String
    suspend fun export(directory: PlatformDirectory): Outcome<ExportError, Unit>
}
