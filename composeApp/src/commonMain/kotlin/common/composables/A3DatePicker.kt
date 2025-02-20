package common.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt

@ExperimentalMaterial3Api
@Composable
fun A3DatePickerDialog(
    value: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
   /**
     * Material3 DatePicker has a timezone handling issue where it internally subtracts
     * the local timezone offset when displaying dates. To compensate:
     * 1. We add the local timezone offset to the input UTC time for display
     * 2. The DatePicker internally subtracts the offset, showing the correct local time
     * 3. When selecting a date, we keep the UTC time as is since it's already correct
     */
    val localOffset = TimeZone.currentSystemDefault()
        .offsetAt(Instant.fromEpochMilliseconds(value))
        .totalSeconds * 1000L
    
    val displayTimeMillis = remember(value) { value + localOffset }
    
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = displayTimeMillis,
        initialDisplayedMonthMillis = displayTimeMillis
    )
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    androidx.compose.material3.DatePickerDialog(onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis!!)
                    onDismiss()
                },
                enabled = confirmEnabled.value,
                content = { Text("OK") }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss, content = { Text("Cancel") })
        },
        content = { DatePicker(state = datePickerState) })
}
