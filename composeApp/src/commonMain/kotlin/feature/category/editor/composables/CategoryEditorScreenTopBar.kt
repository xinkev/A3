package feature.category.editor.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.edit_category
import a3.composeapp.generated.resources.new_category
import a3.composeapp.generated.resources.save
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import common.composables.TopBar
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategoryEditorScreenTopBar(
    isEdit: Boolean,
    addButtonEnabled: Boolean,
    onClickAdd: () -> Unit,
) {
    TopBar(
        title = stringResource(if (isEdit) Res.string.edit_category else Res.string.new_category),
        actions = {
            TextButton(
                enabled = addButtonEnabled,
                onClick = onClickAdd,
                content = {
                    Text(stringResource(if (isEdit) Res.string.save else Res.string.add))
                }
            )
        }
    )
}
