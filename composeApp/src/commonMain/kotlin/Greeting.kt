import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

class Greeting {
    private val platform = getPlatform()
    private val now = Clock.System.now()
    private val formatter = LocalDateTime.Format {
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

    fun greet(): String {
        return "Hello, ${platform.name}!" + " ${formatter.format(now.toLocalDateTime(TimeZone.currentSystemDefault()))}"
    }
}