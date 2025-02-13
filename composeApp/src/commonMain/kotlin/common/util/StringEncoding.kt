package common.util

/**
 * Encodes a string using URL encoding (Percent-encoding).
 * 
 * This function follows RFC 3986 encoding rules:
 * - Alphanumeric characters (a-z, A-Z, 0-9) remain unchanged
 * - Special characters (-_.~) remain unchanged
 * - Spaces are converted to '+'
 * - All other characters are percent-encoded (converted to %HH where HH is the hex value)
 * 
 * Example:
 * ```
 * "Hello World!" -> "Hello+World%21"
 * "你好" -> "%E4%BD%A0%E5%A5%BD"
 * ```
 * 
 * @return The URL-encoded string
 */
fun String.encoded(): String {
    return encodeToByteArray().joinToString(separator = "") { byte ->
        when (byte.toInt().toChar()) {
            // RFC 3986 unreserved characters
            in 'a'..'z', in 'A'..'Z', in '0'..'9', '-', '_', '.', '~' -> byte.toInt().toChar().toString()
            // Space character gets special treatment
            ' ' -> "+"
            // All other characters are percent-encoded
            else -> String.format("%%%02X", byte.toUByte().toInt())
        }
    }
}

/**
 * Decodes a URL-encoded (Percent-encoded) string.
 * 
 * This function handles:
 * - Converting '+' back to spaces
 * - Converting percent-encoded sequences (%HH) back to their original characters
 * - UTF-8 encoded characters (including multi-byte sequences)
 * - Gracefully handles invalid hex sequences by returning them unchanged
 * 
 * Example:
 * ```
 * "Hello+World%21" -> "Hello World!"
 * "%E4%BD%A0%E5%A5%BD" -> "你好"
 * ```
 * 
 * @return The decoded string
 */
fun String.decoded(): String {
    return replace("+", " ") // First, convert '+' back to spaces
        .replace(Regex("(?:%[0-9A-Fa-f]{2})+")) { match ->
            try {
                // Split the matched string into hex values, removing empty results
                val hexString = match.value.split("%")
                    .filter { it.isNotEmpty() }
                
                // Convert hex strings to bytes
                val bytes = hexString.map { hex ->
                    hex.toInt(16).toByte()
                }.toByteArray()
                
                // Convert bytes back to string using platform's default charset (UTF-8)
                bytes.decodeToString()
            } catch (e: NumberFormatException) {
                // If we encounter invalid hex values, return the original sequence unchanged
                match.value
            }
        }
}
