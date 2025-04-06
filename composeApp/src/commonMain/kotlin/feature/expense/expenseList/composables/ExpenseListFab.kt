package feature.expense.expenseList.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add_expenses
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpenseListFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(Res.string.add_expenses)
        )
    }
}
