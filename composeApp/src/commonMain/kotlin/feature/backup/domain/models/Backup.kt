package feature.backup.domain.models

import common.util.A3DateFormat
import common.util.localDateTimeToString
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Backup(
    /**
     * The version of the app that created this backup.
     * This value is only needed for backup purposes and is not need to be provided during restoration.
     * However, it can be used by the adapter to convert the backup to the current app compatible version.
     */
    val appVersion: String?,
    val categories: List<Category>,
    val expenses: List<Expense>
) {
    @Serializable
    data class Expense(
        val uuid: String,
        val detail: String,
        @Contextual
        val datetime: LocalDateTime,
        val category: String,
        val cost: Double
    ) {
        val datetimeISO8601: String
            get() = localDateTimeToString(datetime, A3DateFormat.ISO8601)
    }

    @Serializable
    data class Category(
        val name: String,
        val icon: String
    )
}
