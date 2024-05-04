package model

import kotlinx.serialization.Serializable

@Serializable
data class Backup(
    val expenses: List<Expense>
) {
    @Serializable
    data class Expense(
        val uuid: String = "",
        val timezone: String = "",
        val detail: String,
        val datetime: String = "",
        val category: String = "",
        val cost: Double
    )
}
