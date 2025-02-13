package feature.category.categories.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import app.theme.Dimen
import feature.category.categories.categoryIconMap
import feature.category.common.data.CategoryDataSource
import feature.category.common.domain.model.Category
import feature.category.common.domain.model.CategoryIconName
import org.koin.compose.koinInject

@Composable
fun Categories(
    modifier: Modifier = Modifier,
    selected: Category? = null,
    hasBorder: Boolean = true,
    onSelectionChange: (Category?) -> Unit,
) {
    val isPreview = LocalInspectionMode.current
    if (isPreview) {
        PreviewableContent(modifier)
    } else {
        CategoriesContent(modifier, selected, hasBorder, onSelect = onSelectionChange)
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoriesImpl(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selected: Category?,
    hasBorder: Boolean,
    onSelect: (Category?) -> Unit
) {
    Card(
        modifier = modifier,
        shape = if (hasBorder) {
            CardDefaults.outlinedShape
        } else {
            CardDefaults.shape
        },
        colors = CardDefaults.outlinedCardColors(),
        elevation = CardDefaults.outlinedCardElevation(),
        border = if (hasBorder) {
            CardDefaults.outlinedCardBorder()
        } else {
            null
        },
    ) {
        FlowRow(
            Modifier.padding(Dimen.mediumPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Dimen.smallSize)
        ) {
            categories.forEach { category ->
                val icon = categoryIconMap[category.iconName]
                FilterChip(
                    selected = selected == category,
                    onClick = { onSelect(if (category == selected) null else category) },
                    leadingIcon = {
                        icon?.let {
                            Icon(
                                imageVector = icon.vector(),
                                contentDescription = category.name
                            )
                        }
                    },
                    label = { Text(category.name) }
                )
            }
        }
    }
}

@Composable
private fun CategoriesContent(
    modifier: Modifier = Modifier,
    selected: Category?,
    hasBorder: Boolean,
    dataSource: CategoryDataSource = koinInject(),
    onSelect: (Category?) -> Unit
) {
    val categories by dataSource.getAllCategories().collectAsState(emptyList())

    CategoriesImpl(
        modifier = modifier,
        categories = categories,
        selected = selected,
        hasBorder = hasBorder,
        onSelect = onSelect,
    )
}

@Composable
private fun PreviewableContent(modifier: Modifier = Modifier) {
    val categories = listOf(
        Category(name = "Category 1", iconName = CategoryIconName.IPhone),
        Category(name = "Category 2", iconName = CategoryIconName.Car)
    )
    CategoriesImpl(
        modifier = modifier,
        categories,
        onSelect = { _ -> },
        hasBorder = true,
        selected = null
    )
}

@Preview
@Composable
private fun CategoriesPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewableContent()
    }
}
