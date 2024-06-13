package presentation.feature.expense_editor

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.add
import a3.composeapp.generated.resources.add_notes
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.composables.Categories
import presentation.composables.TopBar
import presentation.model.Category
import presentation.theme.Dimen

object ExpenseEditorScreen {
    @OptIn(KoinExperimentalAPI::class)
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

        LaunchedEffect(Unit) {
            snapshotFlow { datePickerState.selectedDateMillis }
                .collect {
                    vm.onDateChanged(it!!)
                }
        }


        Scaffold(
            topBar = {
                TopBar("", navigateBack = navigateUp)
            },
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = Dimen.largePadding)
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(Dimen.mediumSize),
            ) {
                AmountInput(
                    value = amount,
                    onChange = vm::onAmountChanged
                )
                NoteInput(
                    value = notes,
                    onChange = vm::onNotesChanged
                )

                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.padding(Dimen.largePadding),
                )

                Categories(
                    modifier = Modifier.padding(horizontal = Dimen.largePadding),
                    selected = category,
                    onSelect = vm::onCategoryChanged
                )

                AddButton(
                    enabled = enableAddButton,
                    vm::onClickAdd
                )
            }
        }
    }

    @Composable
    fun NoteInput(
        value: String,
        onChange: (String) -> Unit
    ) {
        TextField(
            value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.largePadding),
            placeholder = { Text(stringResource(Res.string.add_notes)) },
            onValueChange = onChange,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
        )
    }

    @Composable
    fun AmountInput(
        value: String,
        onChange: (String) -> Unit
    ) {
        OutlinedTextField(
            value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.largePadding),
            placeholder = { Text("0.0") },
            onValueChange = {
                val regex = "^\\d*\\.?\\d*$".toRegex()
                if (regex.matches(it)) {
                    onChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
        )
    }

    @Composable
    fun AddButton(
        enabled: Boolean,
        onClick: () -> Unit
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.xLargePadding),
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
