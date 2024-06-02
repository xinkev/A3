package util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val dateTimeFormat = LocalDateTime.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
}

fun parseDateTime(dateTime: String): LocalDateTime {
    return LocalDateTime.parse(
        dateTime,
        format = dateTimeFormat
    )
}

fun dateMillisToLocalDateTime(value: Long): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(value)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}