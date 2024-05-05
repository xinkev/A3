package presentation.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import feature.theme.A3Theme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import model.Expense

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Expenses"
            val icon = rememberVectorPainter(Icons.Default.Money)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val vm = koinScreenModel<HomeViewModel>()
        val data by vm.expenses.collectAsState()

        Body(data)
    }

    @Composable
    fun Body(expenses: List<Expense>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    elevation = 1.dp,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(38.dp)
                        .clickable(role = Role.Button) {

                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Expense",
                            color = Color.White
                        )

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            tint = Color.White
                        )
                    }
                }
            }
            Divider()
            LazyColumn {
                items(expenses) { expense ->
                    TransactionItem(expense)

                    Divider()
                }
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
private fun PreviewHomeTab() {
    A3Theme {
        HomeTab.Body(
            expenses = listOf(
                previewExpense(detail = "Gift", cost = 62.33),
                previewExpense(detail = "GitHub Payment", cost = 7.00),
                previewExpense(detail = "Inboard", cost = 19.99),
                previewExpense(detail = "Chipotle", cost = 9.50)
            )
        )
    }
}

