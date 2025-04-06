package feature.expense.expenseList.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.cancel
import a3.composeapp.generated.resources.delete_expense_warning
import a3.composeapp.generated.resources.yes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteConfirmationDialog(
    onYes: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = { Text(stringResource(Res.string.delete_expense_warning)) },
        confirmButton = {
            TextButton(onYes) {
                Text(stringResource(Res.string.yes), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text(stringResource(Res.string.cancel))
            }
        }
    )
}
