package feature.settings.backup.domain.adapter

interface DataExporter {
    val source: String
    val format: String
    fun export()
}
