package feature.expense.expense_editor.presentation.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.edit_expense
import a3.composeapp.generated.resources.new_expense
import a3.composeapp.generated.resources.save
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import common.composables.TopBar
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopBar(
    enabled: Boolean,
    isEdit: Boolean,
    onClick: () -> Unit,
) {
    TopBar(
        title = stringResource(if (isEdit) Res.string.edit_expense else Res.string.new_expense),
        actions = {
            TextButton(
                content = {
                    Text(stringResource(if (isEdit) Res.string.save else Res.string.add))
                },
                onClick = onClick,
                enabled = enabled
            )
        }
    )
}
