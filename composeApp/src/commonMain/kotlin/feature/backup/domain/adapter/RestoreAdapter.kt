package feature.backup.domain.adapter

import feature.backup.domain.models.Backup

interface RestoreAdapter {
    //    suspend fun export(backup: Backup)
    suspend fun restore(json: String): Backup
}
