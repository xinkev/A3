package util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

private val taiyakiDateTimeFormat = LocalDateTime.Format {
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

fun parseTaiYakiDateTime(dateTime: String): LocalDateTime {
    return LocalDateTime.parse(
        dateTime,
        format = taiyakiDateTimeFormat
    )
}