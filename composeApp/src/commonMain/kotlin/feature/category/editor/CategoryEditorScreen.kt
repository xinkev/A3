package feature.category.editor

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.category_name
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import common.composables.TextInput
import feature.category.editor.composables.CategoryEditorScreenTopBar
import feature.category.editor.composables.CategoryIcons
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import app.theme.Dimen

@Composable
fun CategoryEditorScreen(
    vm: CategoryEditorViewModel = koinViewModel()
) {
    CategoryEditorScreenContent(vm)
}

@Composable
private fun CategoryEditorScreenContent(
    vm: ICategoryEditorViewModel,
) {
    val selectedIconName by vm.selectedIconName.collectAsState()
    val enableAddButton by vm.enableAddButton.collectAsState()

    Column {
        CategoryEditorScreenTopBar(
            addButtonEnabled = enableAddButton,
            onClickAdd = vm::onClickAdd
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Dimen.largePadding),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            TextInput(
                state = vm.nameInputState,
                placeholder = stringResource(Res.string.category_name),
                modifier = Modifier.padding(bottom = Dimen.xLargePadding),
            )
            CategoryIcons(
                selectedIconName = selectedIconName,
                onIconSelect = vm::onIconClick
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    CategoryEditorScreenContent(PreviewCategoryEditorViewModel)
}
