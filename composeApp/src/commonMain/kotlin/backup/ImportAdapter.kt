package backup

interface ImportAdapter {
    //    suspend fun export(backup: Backup)
    suspend fun import(json: String): Backup
}