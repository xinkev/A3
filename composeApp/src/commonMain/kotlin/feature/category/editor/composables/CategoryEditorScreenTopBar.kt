package feature.category.editor.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.new_category
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import common.composables.TopBar
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategoryEditorScreenTopBar(
    addButtonEnabled: Boolean,
    onClickAdd: () -> Unit,
) {
    TopBar(
        title = stringResource(Res.string.new_category),
        actions = {
            TextButton(
                enabled = addButtonEnabled,
                onClick = onClickAdd,
                content = {
                    Text(stringResource(Res.string.add))
                }
            )
        }
    )
}
