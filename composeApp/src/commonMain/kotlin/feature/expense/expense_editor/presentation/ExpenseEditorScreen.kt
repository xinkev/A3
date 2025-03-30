package feature.expense.expense_editor.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import app.theme.Dimen
import com.xinkev.keypad.Keypad
import com.xinkev.keypad.KeypadInput
import feature.category.categories.composables.CategoryPicker
import feature.expense.expense_editor.presentation.composables.DatePickerButton
import feature.expense.expense_editor.presentation.composables.TopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpenseEditorScreen(
    vm: ExpenseEditorViewModel = koinViewModel(),
) {
    ExpenseEditorScreenContent(
        vm = vm,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseEditorScreenContent(
    vm: IExpenseEditorViewModel,
) {
    val dateMillis by vm.dateMillis.collectAsState()
    val category by vm.category.collectAsState()
    val enableAddButton by vm.enableAddButton.collectAsState()

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        initialSelectedDateMillis = dateMillis
    )

    LaunchedEffect(Unit) {
        snapshotFlow { datePickerState.selectedDateMillis }
            .collect {
                vm.onDateChanged(it!!)
            }
    }

    Scaffold(
        topBar = {
            TopBar(
                enabled = enableAddButton,
                isEdit = vm.isEdit,
                onClick = vm::onClickAdd
            )
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(bottom = Dimen.largePadding),
            verticalArrangement = Arrangement.spacedBy(Dimen.mediumSize),
        ) {
            KeypadInput(
                keypadState = vm.keypadState,
                modifier = Modifier
                    .padding(horizontal = Dimen.largePadding)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = Dimen.largePadding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatePickerButton(
                    initialDate = dateMillis,
                    onDateSelected = vm::onDateChanged,
                )
                CategoryPicker(
                    initialSelectedCategory = category,
                    onSelected = vm::onCategoryChanged
                )
            }

            Keypad(vm.keypadState)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        ExpenseEditorScreenContent(
            vm = ExpenseEditorPreviewViewModel
        )
    }
}
