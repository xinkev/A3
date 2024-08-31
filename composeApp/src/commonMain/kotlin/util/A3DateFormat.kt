package util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

enum class A3DateFormat(val value: DateTimeFormat<LocalDateTime>) {
    @Suppress("EnumEntryName")
    `yy-mm-dd hh-mm-ss`(
        LocalDateTime.Format {
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
    ),

    ISO8601(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
            char('T')
            hour()
            char(':')
            minute()
            char(':')
            second()
            char('Z')
        }
    ),

    YYYY_MM_DD(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
        }
    ),

    YYYY_MM_DD_HH_MM(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
            char(' ')
            hour()
            char(':')
            minute()
        }
    )
}