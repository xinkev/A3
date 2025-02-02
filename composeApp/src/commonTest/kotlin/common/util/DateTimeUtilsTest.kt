package common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DateTimeUtilsTest {

    @Test
    fun testParseDateTime_iso8601Format_returnsLocalDateTime() {
        // Arrange
        val dateTimeString = "2023-08-25T10:15:30Z"
        val expected = LocalDateTime(2023, 8, 25, 10, 15, 30)

        // Act
        val result = parseDateTime(dateTimeString)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testDateMillisToLocalDateTime_returnsLocalDateTime() {
        // Arrange
        val millis = 1692957330000L // 2023-08-25T10:15:30Z (UTC)
        val timeZone = TimeZone.of("Asia/Kolkata") // IST (UTC+5:30)
        val expected = LocalDateTime(2023, 8, 25, 15, 25, 30)

        // Act
        val result = Instant.fromEpochMilliseconds(millis).toLocalDateTime(timeZone)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testIsToday_withCurrentDate_returnsTrue() {
        // Arrange
        val currentMillis = now.toEpochMilliseconds()

        // Act
        val result = isToday(currentMillis)

        // Assert
        assertTrue(result)
    }

    @Test
    fun testIsToday_withPastDate_returnsFalse() {
        // Arrange
        val pastMillis = LocalDateTime(2020, 1, 1, 0, 0).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

        // Act
        val result = isToday(pastMillis)

        // Assert
        assertFalse(result)
    }

    @Test
    fun testDateTimeMilliToString_returnsFormattedString() {
        // Arrange
        val millis = 1692957330000L // 2023-08-25T10:15:30Z
        val expected = "2023-08-25T18:55:30Z"

        // Act
        val result = dateTimeMilliToString(millis, A3DateFormat.ISO8601)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testLocalDateTimeToString_returnsFormattedString() {
        // Arrange
        val dateTime = LocalDateTime(2023, 8, 25, 10, 15, 30)
        val expected = "2023-08-25T10:15:30Z"

        // Act
        val result = localDateTimeToString(dateTime, A3DateFormat.ISO8601)

        // Assert
        assertEquals(expected, result)
    }
}
