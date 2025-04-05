package feature.settings.backup.common.domain.adapter

import feature.settings.backup.exporting.domain.adapter.DataExporter
import feature.settings.backup.importing.domain.adapter.DataImporter

interface DataAdapterFactory {
    val supportedFormats: Set<String>
    fun getExporter(source: String, format: String): DataExporter?
    fun getImporter(source: String, format: String): DataImporter?
}
