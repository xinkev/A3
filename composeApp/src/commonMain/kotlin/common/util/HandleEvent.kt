package common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import core.event.A3Event
import core.event.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOn
import org.koin.compose.koinInject
import kotlin.coroutines.CoroutineContext

@Composable
inline fun <reified T: A3Event> HandleEvents(
    eventBus: EventBus = koinInject(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = Dispatchers.Main.immediate,
    noinline onEvent: suspend  CoroutineScope.(event: T) -> Unit,
) {
    val currentOnEvent by rememberUpdatedState(onEvent)
    LaunchedEffect(eventBus.events, lifecycleOwner) {
        eventBus.events
            .flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .flowOn(context)
            .filterIsInstance<T>()
            .collect {
                currentOnEvent(it)
            }
    }
}
