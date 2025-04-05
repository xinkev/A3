package feature.expense.monthlyExpenseList

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import feature.expense.common.domain.model.TotalExpensePerCategory

@Composable
fun ColumnScope.MonthlyExpenseList(
    data: List<TotalExpensePerCategory>
) {

    LazyColumn(modifier = Modifier.weight(1f)) {
        items(data) { expense ->
            ExpensePerCategory(expense)
        }
    }
}
