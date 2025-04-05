package feature.expense.expenseList

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import feature.expense.common.domain.model.Expense
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ColumnScope.ExpenseList(
    expenses: StateFlow<List<Expense>>,
    onClickItem: (Expense) -> Unit,
    onDeleteConfirmed: (Expense) -> Unit,
) {
    val expenseList by expenses.collectAsState()

    LazyColumn(modifier = Modifier.weight(1f)) {
        items(expenseList) { expense ->
            ExpenseListItem(
                expense = expense,
                onClick = { onClickItem(expense) },
                onDeleteConfirmed = { onDeleteConfirmed(expense) }
            )
        }
    }
}
