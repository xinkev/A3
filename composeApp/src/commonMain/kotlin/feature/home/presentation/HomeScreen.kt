package feature.home.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.theme.A3Theme
import feature.home.presentation.composables.HomeFab
import feature.home.presentation.composables.HomeHeader
import feature.home.presentation.composables.TransactionItem
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
            ExpenseList(vm)
        }
    }
}


@Composable
private fun ColumnScope.ExpenseList(
    vm: IHomeViewModel
) {
    val expenses by vm.expenses.collectAsState()

    LazyColumn(modifier = Modifier.weight(1f)) {
        items(expenses) { expense ->
            TransactionItem(
                expense = expense,
                onClick = { vm.onClickTransaction(expense) }
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
