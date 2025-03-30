package feature.home.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.theme.A3Theme
import app.theme.Dimen
import common.util.A3DateFormat
import common.util.dateTimeToDisplay
import common.util.toSmartString
import feature.expense.expense_list.presentation.ExpenseList
import feature.home.presentation.composables.HomeFab
import feature.home.presentation.composables.HomeHeader
import kotlinx.coroutines.flow.StateFlow
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.smallPadding),
                modifier = Modifier
                    .padding(Dimen.largePadding)
            ) {
                DailyTotal(
                    dateMilli = dateMilli,
                    total = vm.currentDateTotal,
                )
                MonthlyTotal(
                    dateMilli = dateMilli,
                    total = vm.currentMonthTotal,
                )
            }
            ExpenseList(
                expenses = vm.expenses,
                onDeleteConfirmed = vm::onDeleteConfirmed,
                onClickItem = vm::onClickExpense
            )
        }
    }
}

@Composable
private fun RowScope.DailyTotal(
    dateMilli: Long,
    total: StateFlow<Double>,
) {
    val currentDate = dateTimeToDisplay(dateMilli,A3DateFormat.DisplayDate)
    val totalExpense by total.collectAsState()

    Card(modifier = Modifier.weight(.5f)) {
        Column(modifier = Modifier.padding(Dimen.mediumPadding)) {
            Text(currentDate, style = MaterialTheme.typography.titleSmall)
            Text(totalExpense.toSmartString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun RowScope.MonthlyTotal(
    dateMilli: Long,
    total: StateFlow<Double>,
) {
    val month = dateTimeToDisplay(dateMilli, A3DateFormat.MonthFullName)
    val totalExpense by total.collectAsState()

    Card(modifier = Modifier.weight(.5f), onClick = {}) {
        Column(modifier = Modifier.padding(Dimen.mediumPadding)) {
            Text(month, style = MaterialTheme.typography.titleSmall)
            Text(totalExpense.toSmartString(), style = MaterialTheme.typography.bodySmall)
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
