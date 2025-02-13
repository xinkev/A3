package feature.category.categories

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.categories
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.theme.Dimen
import common.composables.TopBar
import feature.category.categories.composables.Categories
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesScreen(
    vm: CategoriesViewModel = koinViewModel()
) {
    CategoriesScreenContent(vm)
}

@Composable
private fun CategoriesScreenContent(
    vm: ICategoriesViewModel,
) {
    Column {
        TopBar(stringResource(Res.string.categories))
        Column(
            modifier = Modifier
                .padding(horizontal = Dimen.largePadding)
                .padding(bottom = Dimen.largePadding),
            verticalArrangement = Arrangement.spacedBy(Dimen.smallSize)
        ) {
            Categories(
                modifier = Modifier.weight(1f),
                selected = null,
                onSelectionChange = vm::onClickCategory,
            )
            Button(
                onClick = vm::onClickAdd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.add))
            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    CategoriesScreenContent(PreviewCategoriesViewModel)
}
