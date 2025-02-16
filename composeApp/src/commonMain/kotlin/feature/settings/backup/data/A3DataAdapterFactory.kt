package feature.settings.backup.data

import feature.settings.backup.domain.adapter.DataAdapterFactory
import feature.settings.backup.domain.adapter.DataExporter
import feature.settings.backup.domain.adapter.DataImporter

class A3DataAdapterFactory(
    exporters: Set<DataExporter>,
    importers: Set<DataImporter>,
) : DataAdapterFactory {
    private val exporterMap = exporters.associateBy { it.source }
    private val importerMap = importers.associateBy { it.source }
    override val supportedFormats: Set<String> = run {
        val formats = importers.map { it.format } + exporters.map { it.format }
        formats.toSet()
    }

    override fun getExporter(source: String, format: String): DataExporter? {
        return exporterMap[source]?.takeIf { format == it.format }
    }

    override fun getImporter(source: String, format: String): DataImporter? {
        return importerMap[source]?.takeIf { format == it.format }
    }
}
