package feature.expense.expenseList

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.theme.A3Theme
import feature.expense.expenseList.composables.ExpenseList
import feature.expense.expenseList.composables.ExpenseListFab
import feature.expense.expenseList.composables.ExpenseListScreenHeader
import feature.expense.expenseList.composables.ExpenseListScreenTab.Daily
import feature.expense.expenseList.composables.ExpenseListScreenTab.Monthly
import feature.expense.expenseList.composables.ExpenseListScreenTabs
import feature.expense.monthlyExpenseList.MonthlyExpenseList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpenseListScreen(
    vm: ExpenseListViewModel = koinViewModel(),
) {
    ExpenseListScreenContent(vm = vm)
}

@Composable
fun ExpenseListScreenContent(
    vm: IExpenseListViewModel,
) {
    val dateMilli by vm.dateMillis.collectAsState()
    val expensePerCategory by vm.totalExpensePerCategory.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExpenseListFab(onClick = vm::onClickAddExpense)
        }
    ) {
        Column {
            ExpenseListScreenHeader(
                initialDate = dateMilli,
                onDateSelected = vm::setDate
            )
            ExpenseListScreenTabs(
                dateMilli = dateMilli,
                dailyTotal = vm.currentDateTotal,
                monthlyTotal = vm.currentMonthTotal,
            ) { selectedTab ->
                when (selectedTab) {
                    Daily -> {
                        ExpenseList(
                            expenses = vm.expenses,
                            onDeleteConfirmed = vm::onDeleteConfirmed,
                            onClickItem = vm::onClickExpense
                        )
                    }

                    Monthly -> {
                        MonthlyExpenseList(expensePerCategory)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    A3Theme {
        ExpenseListScreenContent(
            vm = PreviewExpenseListViewModel
        )
    }
}
