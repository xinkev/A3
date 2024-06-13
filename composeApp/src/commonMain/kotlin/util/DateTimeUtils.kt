package util

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.today
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource

val now: Instant
    get() = Clock.System.now()

fun parseDateTime(
    dateTime: String,
    format: A3DateFormat = A3DateFormat.ISO8601
): LocalDateTime {
    return LocalDateTime.parse(
        dateTime,
        format = format.value,
    )
}

fun dateMillisToLocalDateTime(value: Long): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(value)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun isToday(value: Long, timeZone: TimeZone = TimeZone.currentSystemDefault()): Boolean {
    val instant = Instant.fromEpochMilliseconds(value)
    return instant.toLocalDateTime(timeZone).date == now.toLocalDateTime(timeZone).date
}

@Composable
fun dateTimeToDisplay(value: Long, format: A3DateFormat): String {
    if (isToday(value)) return stringResource(Res.string.today)
    return dateMillisToLocalDateTime(value).format(format.value)
}

fun dateTimeMilliToString(value: Long, format: A3DateFormat): String {
    return dateMillisToLocalDateTime(value).format(format.value)
}

fun localDateTimeToString(value: LocalDateTime, format: A3DateFormat): String {
    return value.format(format.value)
}