package common.util

/**
 * Formats a Float value to string.
 *
 * - If the Float has no decimal part (e.g., 20.0), it is converted to an Int and displayed as "20".
 * - If the Float has a decimal part (e.g., 20.5), it is displayed as is.
 *
 * @receiver The Float value to format.
 * @return A formatted String representation of the number.
 */
fun Double.toSmartString(): String {
    return if (this % 1 == 0.0) this.toInt().toString() else this.toString()
}
