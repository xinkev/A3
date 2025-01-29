package presentation.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import util.A3DateFormat
import util.dateTimeToDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun A3DatePicker(
    modifier: Modifier = Modifier,
    value: Long,
    onDateSelected: (Long) -> Unit
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

    OutlinedButton(
        modifier = modifier,
        onClick = { openDialog.value = true },
        content = {
            Text(dateTimeToDisplay(value, A3DateFormat.DisplayDate))
        }
    )

    if (openDialog.value) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = value)
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDateSelected(datePickerState.selectedDateMillis!!)
                    },
                    enabled = confirmEnabled.value,
                    content = { Text("OK") }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { openDialog.value = false },
                    content = { Text("Cancel") }
                )
            },
            content = { DatePicker(state = datePickerState) }
        )
    }
}
