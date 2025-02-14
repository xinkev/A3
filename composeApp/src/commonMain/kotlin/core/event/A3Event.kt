package core.event

sealed interface A3Event

/**
 * For testing
 */
class TestEvent(val id: Int) : A3Event
