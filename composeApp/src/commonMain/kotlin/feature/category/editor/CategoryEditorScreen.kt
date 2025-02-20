package feature.category.editor

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.category_name
import a3.composeapp.generated.resources.category_name_taken_error
import a3.composeapp.generated.resources.save
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.theme.Dimen
import common.composables.TextInput
import feature.category.editor.composables.CategoryEditorScreenTopBar
import feature.category.editor.composables.CategoryIcons
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

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
    val nameIsTaken by vm.nameIsTaken.collectAsState()

    Column {
        CategoryEditorScreenTopBar(
            isEdit = vm.isEdit,
            addButtonEnabled = enableAddButton,
            onDeleteConfirmed = vm::onDeleteConfirmed,
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
                error = if (nameIsTaken) stringResource(Res.string.category_name_taken_error) else null
            )
            CategoryIcons(
                selectedIconName = selectedIconName,
                onIconSelect = vm::onIconClick
            )
            Button(
                onClick = vm::onClickAdd,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(if (vm.isEdit) Res.string.save else Res.string.add))
            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    CategoryEditorScreenContent(PreviewCategoryEditorViewModel)
}
