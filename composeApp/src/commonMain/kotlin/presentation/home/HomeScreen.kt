package presentation.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import feature.theme.A3Theme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import model.Expense
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

object HomeScreen {

    @OptIn(KoinExperimentalAPI::class)
    @Composable
    fun Content(
        vm: HomeViewModel = koinViewModel()
    ) {
        val data by vm.expenses.collectAsState()

        ExpenseList(data)
    }

    @Composable
    fun ExpenseList(
        expenses: List<Expense>
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
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

    @Preview
    @Composable
    private fun PreviewHomeScreen() {
        A3Theme {
            ExpenseList(listOf(
                previewExpense(detail = "Gift", cost = 62.33),
                previewExpense(detail = "GitHub Payment", cost = 7.00),
                previewExpense(detail = "Inboard", cost = 19.99),
                previewExpense(detail = "Chipotle", cost = 9.50)
            ))
        }
    }
}