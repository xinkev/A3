package common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode

/**
 * For some reasons(maybe because of the hack), LocalInspectionMode is not true in @Preview.
 * This is a workaround.
 */
@Composable
fun preview(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        content()
    }
}
