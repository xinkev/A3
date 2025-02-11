package core.event

import androidx.compose.runtime.TestOnly

sealed interface A3Event

/**
 * For testing
 */
class TestEvent @TestOnly constructor(val id: Int) : A3Event
