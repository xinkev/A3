package feature.settings.backup.domain.adapter

interface DataAdapterFactory {
    val supportedFormats: Set<String>
    fun getExporter(source: String, format: String): DataExporter?
    fun getImporter(source: String, format: String): DataImporter?
}
