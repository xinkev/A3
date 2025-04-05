package feature.expense.expenseEditor.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import common.composables.A3DatePickerDialog
import common.util.A3DateFormat
import common.util.dateTimeToDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
) {
    var openDialog by rememberSaveable { mutableStateOf(false) }

    OutlinedButton(
        onClick = { openDialog = true },
        content = {
            Text(dateTimeToDisplay(initialDate, A3DateFormat.DisplayDate))
        }
    )
    if (openDialog) {
        A3DatePickerDialog(
            onDismiss = { openDialog = false},
            value = initialDate,
            onDateSelected = onDateSelected
        )
    }
}
