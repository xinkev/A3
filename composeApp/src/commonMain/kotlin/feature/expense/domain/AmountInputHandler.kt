package feature.expense.domain

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope

class AmountInputHandler(
    coroutineScope: CoroutineScope,
    private val state: TextFieldState,
) {
    val stateFlow = snapshotFlow { state.text }
}
