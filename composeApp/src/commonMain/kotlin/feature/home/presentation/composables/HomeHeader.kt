package feature.home.presentation.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.next_month
import a3.composeapp.generated.resources.prev_month
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.theme.Dimen
import common.composables.A3DatePickerDialog
import common.util.A3DateFormat
import common.util.dateTimeToDisplay
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }
    val prevMonth= remember(initialDate) {
        plusOrMinusMonth(initialDate, -1)
    }
    val nextMonth = remember(initialDate) {
        plusOrMinusMonth(initialDate, 1)
    }

    A3DatePickerDialog(
        opened = openDialog,
        value = initialDate,
        onDateSelected = onDateSelected
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.largePadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            onDateSelected(prevMonth)
        }) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = stringResource(Res.string.prev_month),
            )
        }

        TextButton(onClick = { openDialog.value = true }) {
            Text(
                dateTimeToDisplay(initialDate, A3DateFormat.DisplayDate),
                style = MaterialTheme.typography.titleLarge
            )
        }

        IconButton(onClick = {
            onDateSelected(nextMonth)
        }) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(Res.string.next_month),
            )
        }
    }
}

private fun plusOrMinusMonth(
    currentDate: Long,
    count: Int,
): Long {
    var instant = Instant.fromEpochMilliseconds(currentDate)
    instant = if (count > 0) {
        instant.plus(DateTimePeriod(months = 1), TimeZone.currentSystemDefault())
    } else {
        instant.minus(DateTimePeriod(months = 1), TimeZone.currentSystemDefault())
    }
    return instant.toEpochMilliseconds()
}

@Preview
@Composable
private fun Preview() {
    HomeHeader(
        initialDate = 1L,
        onDateSelected = {}
    )
}
