package feature.home.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.theme.A3Theme
import feature.expense.expenseList.ExpenseList
import feature.expense.monthlyExpenseList.MonthlyExpenseList
import feature.home.presentation.composables.HomeFab
import feature.home.presentation.composables.HomeHeader
import feature.home.presentation.composables.HomeTab.Daily
import feature.home.presentation.composables.HomeTab.Monthly
import feature.home.presentation.composables.HomeTabs
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    vm: HomeViewModel = koinViewModel(),
) {
    HomeScreenContent(vm = vm)
}

@Composable
fun HomeScreenContent(
    vm: IHomeViewModel,
) {
    val dateMilli by vm.dateMillis.collectAsState()
    val expensePerCategory by vm.totalExpensePerCategory.collectAsState()

    Scaffold(
        floatingActionButton = {
            HomeFab(onClick = vm::onClickAddExpense)
        }
    ) {
        Column {
            HomeHeader(
                initialDate = dateMilli,
                onDateSelected = vm::setDate
            )
            HomeTabs(
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
private fun PreviewHomeScreen() {
    A3Theme {
        HomeScreenContent(
            vm = PreviewHomeViewModel
        )
    }
}
