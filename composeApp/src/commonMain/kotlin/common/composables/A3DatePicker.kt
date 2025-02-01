package common.composables

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import common.util.A3DateFormat
import common.util.dateTimeToDisplay

enum class A3DatePickerButtonType {
    Outlined, Text, Filled
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun A3DatePicker(
    modifier: Modifier = Modifier,
    value: Long,
    buttonType: A3DatePickerButtonType = A3DatePickerButtonType.Outlined,
    onDateSelected: (Long) -> Unit
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

    when (buttonType) {
        A3DatePickerButtonType.Outlined -> {
            OutlinedButton(
                modifier = modifier,
                onClick = { openDialog.value = true },
                content = {
                    Text(dateTimeToDisplay(value, A3DateFormat.DisplayDate))
                }
            )
        }

        A3DatePickerButtonType.Text -> {
            TextButton(
                modifier = modifier,
                onClick = { openDialog.value = true },
                content = {
                    Text(dateTimeToDisplay(value, A3DateFormat.DisplayDate))
                }
            )
        }

        A3DatePickerButtonType.Filled -> {
            FilledTonalButton(
                modifier = modifier,
                onClick = { openDialog.value = true },
                content = {
                    Text(dateTimeToDisplay(value, A3DateFormat.DisplayDate))
                }
            )
        }
    }

    DatePickerDialog(
        opened = openDialog,
        value = value,
        onDateSelected = onDateSelected
    )
}

@ExperimentalMaterial3Api
@Composable
private fun DatePickerDialog(
    opened: MutableState<Boolean>,
    value: Long,
    onDateSelected: (Long) -> Unit
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
