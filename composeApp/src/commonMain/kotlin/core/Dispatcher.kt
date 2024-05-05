package core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface Dispatchers {
    val io: CoroutineDispatcher
}

class DispatchersImpl : core.Dispatchers {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}