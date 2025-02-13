package feature.category.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val uuid: String,
    val name: String,
    val iconName: CategoryIconName?,
)
