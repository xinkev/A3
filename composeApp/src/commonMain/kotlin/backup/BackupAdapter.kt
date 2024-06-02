package backup

interface BackupAdapter {
    //    suspend fun export(backup: Backup)
    suspend fun import(json: String): Backup
}