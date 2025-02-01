package feature.home.presentation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add_expenses
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.composables.A3DatePicker
import common.domain.model.Category
import feature.expense.domain.model.Expense
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import theme.A3Theme
import theme.Dimen

@Composable
fun HomeScreen(
    vm: HomeViewModel = koinViewModel(),
    navigateToExpenseEditor: () -> Unit,
) {
    HomeScreenContent(
        vm = vm,
        onTapAdd = navigateToExpenseEditor
    )
}

@Composable
fun HomeScreenContent(
    vm: IHomeViewModel,
    onTapAdd: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(8.dp)
        ) {
            A3DatePicker(
                modifier = Modifier.padding(horizontal = Dimen.mediumPadding),
                onDateSelected = vm::setDate,
                value = vm.dateMillis.collectAsState().value
            )
            Spacer(modifier = Modifier.weight(1f))
            AddButton(onClick = onTapAdd)
        }
        ExpenseList(vm)
    }
}


@Composable
private fun ColumnScope.ExpenseList(
    vm: IHomeViewModel
) {
    val expenses by vm.expenses.collectAsState()
    val categories by vm.categories.collectAsState()

    LazyColumn(modifier = Modifier.weight(1f)) {
        items(expenses) { expense ->
            TransactionItem(expense, categories)

            HorizontalDivider()
        }
    }
}

@Composable
private fun AddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FilledTonalIconButton(
        modifier = modifier,
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(Res.string.add_expenses)
            )
        },
        onClick = onClick
    )
}

@Composable
private fun TransactionItem(expense: Expense, categories: List<Category>) {
    val category = remember { categories.firstOrNull { it.name == expense.category } }
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimen.smallPadding),
            modifier = Modifier.weight(0.7f),
        ) {
            category?.icon?.let {
                Icon(
                    imageVector = it.vector(),
                    contentDescription = category.name,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = expense.detail ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = "${expense.cost}",
            modifier = Modifier.weight(0.3f),
            textAlign = TextAlign.End
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    A3Theme {
        HomeScreenContent(
            onTapAdd = {},
            vm = PreviewHomeViewModel
        )
    }
}
