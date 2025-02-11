package core.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn

class EventBus(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    bufferCapacity: Int = Channel.UNLIMITED,
) {
    private val coroutineScope = CoroutineScope(dispatcher + SupervisorJob())
    private val _channel = Channel<A3Event>(bufferCapacity)

    val events = _channel.receiveAsFlow()
        .shareIn(coroutineScope,
            started = SharingStarted.Lazily,
            replay = 0
        )

    suspend fun send(event: A3Event) {
        _channel.send(event)
    }
}
