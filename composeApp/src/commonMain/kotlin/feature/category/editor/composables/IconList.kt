package feature.category.editor.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.category.common.domain.model.CategoryIconName
import feature.category.categories.categoryIconMap
import app.theme.Dimen

@Composable
fun ColumnScope.CategoryIcons(
    selectedIconName: CategoryIconName?,
    modifier: Modifier = Modifier,
    onIconSelect: (CategoryIconName) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(48.dp),
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(Dimen.smallSize),
        verticalArrangement = Arrangement.spacedBy(Dimen.smallSize),
    ) {
        items(categoryIconMap.toList()) { icon ->
            FilterChip(
                selected = selectedIconName == icon.first,
                onClick = { onIconSelect(icon.first) },
                label = {
                    Icon(
                        imageVector = icon.second.vector(),
                        contentDescription = icon.first.realName
                    )
                }
            )
        }
    }
}
