package com.xinkev.keypad

import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.notkamui.keval.KevalException
import com.notkamui.keval.keval

@Stable
internal class KeyPressHandler(
    private val keypadState: KeypadState
) {
    private val replacements = mapOf(
        '‒' to "-",
        '×' to "*",
        '÷' to "/",
    )
    var error by mutableStateOf("")

    fun onKeyPress(key: KeypadKey) {
        keypadState.amountEvalError = ""

        when (key) {
            is StandardKey -> insertOrReplace(key)
            is OperatorKey -> insertOrReplace(key)
            is BackspaceKey -> backspace()
            is EqualKey -> calculate()
            BracketKey.Open -> insertOrReplace(key)
            BracketKey.Close -> insertOrReplace(key)
        }
    }

    private fun insertOrReplace(key: KeypadKey) {
        keypadState.amount.edit {
            if (selection.collapsed) {
                insert(selection.start, key.text)
            } else {
                replace(selection.start, selection.end, key.text)
            }
        }
    }

    private fun backspace() {
        if (keypadState.amount.text.isEmpty()) return

        keypadState.amount.edit {
            delete(selection.start - 1, selection.end)
        }
    }

    private fun calculate() {
        if (keypadState.amount.text.isEmpty()) return

        val expression = keypadState.amount.text.replaceCharacters(replacements)
        try {
            val result = expression.keval()
            keypadState.amount.edit {
                replace(0, length, result.toSmartString())
            }
        } catch (e: KevalException) {
            val errorMessage =
                e.message?.replaceCharacters(replacements.entries.associate { it.value.first() to it.key.toString() })
                    ?: "Evaluation failed"
            keypadState.amountEvalError = errorMessage
        }
    }

    /**
     * Replaces characters in the CharSequence based on the provided replacements map.
     *
     * This function uses a regular expression to find characters in the CharSequence that match
     * any of the keys in the replacements map. It then replaces each matched character with its
     * corresponding value from the replacements map.
     *
     * @receiver The CharSequence to perform replacements on.
     * @param replacements A map of characters to replace and their corresponding replacement values.
     * @return A new String with the characters replaced according to the replacements map.
     */
    private fun CharSequence.replaceCharacters(replacements: Map<Char, String>): String {
        val regex = "[${replacements.keys.joinToString("")}]".toRegex()
        return regex.replace(this) { match ->
            replacements[match.value.first()] ?: match.value
        }
    }

    /**
     * Formats a Float value to string.
     *
     * - If the Float has no decimal part (e.g., 20.0), it is converted to an Int and displayed as "20".
     * - If the Float has a decimal part (e.g., 20.5), it is displayed as is.
     *
     * @receiver The Float value to format.
     * @return A formatted String representation of the number.
     */
    private fun Double.toSmartString(): String {
        return if (this % 1 == 0.0) this.toInt().toString() else this.toString()
    }
}
