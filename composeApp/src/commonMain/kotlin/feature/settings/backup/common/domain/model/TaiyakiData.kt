package feature.settings.backup.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TaiyakiData(
    /**
     * The version of the app that created this backup.
     * This value is only needed for backup purposes and is not need to be provided during restoration.
     * However, it can be used by the adapter to convert the backup to the current app compatible version.
     */
    val version: String?,
    val categories: List<Category>,
    val expenses: List<Expense>
) {
    @Serializable
    data class Expense(
        val uuid: String,
        val detail: String,
        val datetime: String,
        val category: String,
        val cost: String,
        val timezone: String,
    )

    @Serializable
    data class Category(
        val name: String,
        val icon: String
    )
}
