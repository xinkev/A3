package backup

interface RestoreAdapter {
    //    suspend fun export(backup: Backup)
    suspend fun restore(json: String): Backup
}