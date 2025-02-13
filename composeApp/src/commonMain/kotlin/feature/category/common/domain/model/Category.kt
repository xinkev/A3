package feature.category.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val iconName: CategoryIconName?,
)
