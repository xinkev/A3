package common.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@ExperimentalMaterial3Api
@Composable
fun A3DatePickerDialog(
    opened: MutableState<Boolean>,
    value: Long, onDateSelected: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = value)
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    if (opened.value) {
        androidx.compose.material3.DatePickerDialog(
            onDismissRequest = { opened.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        opened.value = false
                        onDateSelected(datePickerState.selectedDateMillis!!)
                    },
                    enabled = confirmEnabled.value,
                    content = { Text("OK") }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { opened.value = false },
                    content = { Text("Cancel") }
                )
            },
            content = { DatePicker(state = datePickerState) }
        )
    }
}
