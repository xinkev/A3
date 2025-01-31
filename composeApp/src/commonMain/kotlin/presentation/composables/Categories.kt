package presentation.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tag
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
import androidx.compose.ui.unit.dp
import data.CategoryDataSource
import org.koin.compose.koinInject
import presentation.model.Category
import presentation.model.MaterialIcon
import presentation.theme.Dimen

// TODO: Make onSelect only invokable when a category is selected
@Composable
fun Categories(
    modifier: Modifier = Modifier,
    selected: Category? = null,
    hasBorder: Boolean = true,
    onSelect: (Category?) -> Unit,
) {
    val isPreview = LocalInspectionMode.current
    if (isPreview) {
        PreviewableContent(modifier)
    } else {
        Content(modifier, selected, hasBorder, onSelect)
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Impl(
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
                .requiredSizeIn(maxHeight = 300.dp)
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Dimen.smallSize)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = selected == category,
                    onClick = { onSelect(if (category == selected) null else category) },
                    leadingIcon = {
                        category.icon?.let {
                            Icon(
                                imageVector = it.vector(),
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
private fun PreviewableContent(modifier: Modifier = Modifier) {
    val categories = listOf(
        Category(name = "Category 1", icon = MaterialIcon(Icons.Outlined.Tag)),
        Category(name = "Category 2", icon = null)
    )
    Impl(modifier = modifier, categories, onSelect = { _ -> }, hasBorder = true, selected = null)
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    selected: Category?,
    hasBorder: Boolean,
    onSelect: (Category?) -> Unit
) {
    val ds = koinInject<CategoryDataSource>()
    val categories by ds.getAllCategories().collectAsState(emptyList())

    Impl(
        modifier = modifier,
        categories = categories,
        selected = selected,
        hasBorder = hasBorder,
        onSelect = onSelect,
    )
}

@Preview
@Composable
private fun CategoriesPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        PreviewableContent()
    }
}