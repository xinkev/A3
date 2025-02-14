package feature.expense.editor.presentation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.edit_expense
import a3.composeapp.generated.resources.new_expense
import a3.composeapp.generated.resources.save
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import common.composables.A3DatePicker
import common.composables.A3DatePickerButtonType
import common.composables.TopBar
import feature.category.categories.composables.CategoryPicker
import org.jetbrains.compose.resources.stringResource
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
                title = stringResource(if (vm.isEdit) Res.string.edit_expense else Res.string.new_expense),
                actions = {
                    AddButton(
                        enabled = enableAddButton,
                        onClick = vm::onClickAdd,
                        isEdit = vm.isEdit,
                    )
                }
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
                modifier = Modifier.padding(horizontal = Dimen.largePadding).fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(horizontal = Dimen.largePadding).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                A3DatePicker(
                    value = dateMillis,
                    onDateSelected = vm::onDateChanged,
                    buttonType = A3DatePickerButtonType.Text
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

@Composable
private fun AddButton(
    enabled: Boolean,
    isEdit: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        content = {
            Text(stringResource(if (isEdit) Res.string.save else Res.string.add))
        },
        onClick = onClick,
        enabled = enabled
    )
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
