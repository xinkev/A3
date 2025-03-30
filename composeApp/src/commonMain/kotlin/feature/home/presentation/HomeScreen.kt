package feature.home.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val date by vm.dateMillis.collectAsState()
    val currentMonthTotal by vm.currentMonthTotal.collectAsState()
    val currentDateTotal by vm.currentDateTotal.collectAsState()
    val displayDate = dateTimeToDisplay(date, A3DateFormat.DisplayDate)
    val currentMonthLabel = dateTimeToDisplay(date, A3DateFormat.MonthFullName)

    Scaffold(
        floatingActionButton = {
            HomeFab(onClick = vm::onClickAddExpense)
        }
    ) {
        Column {
            HomeHeader(
                initialDate = date,
                onDateSelected = vm::setDate
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.smallPadding),
                modifier = Modifier
                    .padding(Dimen.largePadding)
            ) {
                Card(modifier = Modifier.weight(.5f)) {
                    Column(modifier = Modifier.padding(Dimen.mediumPadding)) {
                        Text(displayDate, style = MaterialTheme.typography.titleSmall)
                        Text(currentDateTotal.toSmartString(), style = MaterialTheme.typography.bodySmall)
                    }
                }
                Card(modifier = Modifier.weight(.5f), onClick = {}) {
                    Column(modifier = Modifier.padding(Dimen.mediumPadding)) {
                        Text(currentMonthLabel, style = MaterialTheme.typography.titleSmall)
                        Text(currentMonthTotal.toSmartString(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            ExpenseList(
                expenses = vm.expenses,
                onDeleteConfirmed = vm::onDeleteConfirmed,
                onClickItem = vm::onClickExpense
            )
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
