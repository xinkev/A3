package common.util

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.today
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

val now: Instant
    get() =  Clock.System.now()

fun parseDateTime(
    dateTime: String,
    format: A3DateFormatDateTimeComponents = A3DateFormatDateTimeComponents.TaiyakiDateTime,
    timeZone: TimeZone = TimeZone.UTC,
): LocalDateTime {
    return Instant.parse(dateTime, format.value).toLocalDateTime(timeZone)
}

fun dateMillisToLocalDateTime(
    value: Long,
    timeZone: TimeZone = TimeZone.UTC
): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(value)
    return instant.toLocalDateTime(timeZone)
}

fun isToday(value: Long, timeZone: TimeZone = TimeZone.UTC): Boolean {
    val instant = Instant.fromEpochMilliseconds(value)
    return instant.toLocalDateTime(timeZone).date == now.toLocalDateTime(timeZone).date
}

@Composable
fun dateTimeToDisplay(value: Long, format: A3DateFormat): String {
    if (isToday(value) && format != A3DateFormat.MonthFullName) return stringResource(Res.string.today)
    // Use the default system timezone for displaying the date
    return remember(value) {
        dateMillisToLocalDateTime(value, TimeZone.currentSystemDefault()).format(format.value)
    }
}

fun dateTimeMilliToString(value: Long, format: A3DateFormat): String {
    return dateMillisToLocalDateTime(value).format(format.value)
}

fun localDateTimeToString(value: LocalDateTime, format: A3DateFormat): String {
    return value.format(format.value)
}

fun localDateTimeToMillis(
    value: LocalDateTime,
    timeZone: TimeZone = TimeZone.UTC
): Long {
    return value.toInstant(timeZone).toEpochMilliseconds()
}
