package presentation.feature.expense_editor

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.composables.A3DatePicker
import presentation.composables.A3DatePickerButtonType
import presentation.composables.CategoryPicker
import presentation.composables.TopBar
import presentation.feature.expense_editor.keypad.Keypad
import presentation.feature.expense_editor.keypad.KeypadInput
import presentation.model.Category
import presentation.theme.Dimen

object ExpenseEditorScreen {

    @Composable
    fun View(
        vm: ExpenseEditorViewModel = koinViewModel(),
        navigateUp: () -> Unit
    ) {
        Content(
            vm = vm,
            navigateUp = navigateUp,
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        vm: IExpenseEditorViewModel,
        navigateUp: () -> Unit,
    ) {
        val dateMillis by vm.dateMillis.collectAsState()
        val amount by vm.amount.collectAsState()
        val notes by vm.notes.collectAsState()
        val category by vm.category.collectAsState()
        val enableAddButton by vm.enableAddButton.collectAsState()

        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Input,
            initialSelectedDateMillis = dateMillis
        )
        val inputState = rememberTextFieldState()

        LaunchedEffect(Unit) {
            snapshotFlow { datePickerState.selectedDateMillis }
                .collect {
                    vm.onDateChanged(it!!)
                }
        }

        Scaffold(
            topBar = {
                TopBar(
                    title = "New Expense",
                    navigateBack = navigateUp,
                    actions = {
                        AddButton(
                            enabled = enableAddButton,
                            onClick = vm::onClickAdd
                        )
                    }
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(Dimen.mediumSize),
            ) {
                KeypadInput(
                    amountState = inputState,
                    noteState = inputState,
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
                        selectedCategory = category,
                        onSelect = vm::onCategoryChanged
                    )
                }

                Keypad { vm.onAmountChanged(amount + it.text) }
            }
        }
    }

    @Composable
    fun AddButton(
        enabled: Boolean,
        onClick: () -> Unit
    ) {
        TextButton(
            content = {
                Text(stringResource(Res.string.add))
            },
            onClick = onClick,
            enabled = enabled
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        ExpenseEditorScreen.Content(
            navigateUp = {},
            vm = object : IExpenseEditorViewModel {
                override val amount: StateFlow<String>
                    get() = MutableStateFlow("")
                override val notes: StateFlow<String>
                    get() = MutableStateFlow("")
                override val dateMillis: StateFlow<Long>
                    get() = MutableStateFlow(0)
                override val category: StateFlow<Category?>
                    get() = MutableStateFlow(null)
                override val enableAddButton: StateFlow<Boolean>
                    get() = MutableStateFlow(false)

                override fun onAmountChanged(amount: String) {
                }

                override fun onNotesChanged(notes: String) {
                }

                override fun onDateChanged(dateMillis: Long) {
                }

                override fun onCategoryChanged(category: Category?) {
                }

                override fun onClickAdd() {
                }

            }
        )
    }
}
