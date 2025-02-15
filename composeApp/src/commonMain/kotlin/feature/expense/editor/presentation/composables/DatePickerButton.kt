package feature.expense.editor.presentation.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import common.composables.A3DatePickerDialog
import common.util.A3DateFormat
import common.util.dateTimeToDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

    OutlinedButton(
        onClick = { openDialog.value = true },
        content = {
            Text(dateTimeToDisplay(initialDate, A3DateFormat.DisplayDate))
        }
    )
    A3DatePickerDialog(
        opened = openDialog,
        value = initialDate,
        onDateSelected = onDateSelected
    )
}
