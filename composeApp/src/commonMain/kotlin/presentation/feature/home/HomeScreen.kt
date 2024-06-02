package presentation.feature.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.theme.A3Theme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import presentation.model.Expense
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

object HomeScreen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    fun View(
        vm: HomeViewModel = koinViewModel(),
        navigateToExpenseEditor: () -> Unit,
    ) {
        val expenses by vm.expenses.collectAsState()

        Content(
            expenses = expenses,
            onTapAdd = navigateToExpenseEditor
        )
    }

    @Composable
    fun Content(
        expenses: List<Expense>,
        onTapAdd: () -> Unit
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                FilledTonalIconButton(
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(Res.string.add_expenses)
                        )
                    },
                    onClick = onTapAdd
                )
            }
            ExpenseList(expenses)
        }
    }

    @Composable
    fun ColumnScope.ExpenseList(
        expenses: List<Expense>
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(expenses) { expense ->
                TransactionItem(expense)

                HorizontalDivider()
            }
        }
    }

    @Composable
    fun TransactionItem(expense: Expense) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = expense.detail ?: "",
                modifier = Modifier.weight(0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${expense.cost}",
                modifier = Modifier.weight(0.3f),
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    A3Theme {
        HomeScreen.Content(
            expenses = listOf(
                previewExpense(detail = "Gift", cost = 62.33),
                previewExpense(detail = "GitHub Payment", cost = 7.00),
                previewExpense(detail = "Inboard", cost = 19.99),
                previewExpense(detail = "Chipotle", cost = 9.50)
            ),
            onTapAdd = {}
        )
    }
}

private fun previewExpense(detail: String, cost: Double): Expense {
    return Expense(
        uuid = "",
        category = "",
        cost = cost,
        detail = detail,
        datetime = LocalDateTime(2024, 5, 5, 16, 27, 30),
        timezone = TimeZone.of("Asia/Tokyo")
    )
}