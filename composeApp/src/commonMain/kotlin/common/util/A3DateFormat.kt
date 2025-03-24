package common.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

enum class A3DateFormatDateTimeComponents(val value: DateTimeFormat<DateTimeComponents>) {
    TaiyakiDateTime(DateTimeComponents.Format {
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

    })
}

enum class A3DateFormat(val value: DateTimeFormat<LocalDateTime>) {
    DisplayDateTime(
        LocalDateTime.Format
        {
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
        LocalDateTime.Format
        {
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

    DisplayDate(
        LocalDateTime.Format
        {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
        }
    ),

    DisplayDateTimeWithoutSeconds(
        LocalDateTime.Format
        {
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
    ),

    FileName(
        LocalDateTime.Format {
            year()
            monthNumber()
            dayOfMonth()
            hour()
            minute()
            second()
        }
    ),

    MonthFullName(
        LocalDateTime.Format {
            monthName(MonthNames.ENGLISH_FULL)
        }
    )
}
