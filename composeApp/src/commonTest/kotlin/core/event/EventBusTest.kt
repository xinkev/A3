package core.event

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class EventBusTest {
    private lateinit var eventBus: EventBus

    @BeforeTest
    fun setup() {
        eventBus = EventBus()
    }

    @Test
    fun `sent event is received by collector`() = runTest {
        val testEvent = TestEvent(1)

        launch {
            eventBus.send(testEvent)
        }

        eventBus.events.test {
            assertEquals(awaitItem(), testEvent)
        }
    }

    @Test
    fun `all subscribers should receive the event`() = runTest {
        val event = TestEvent(1)

        launch {
            eventBus.send(event)
        }
        turbineScope {
            val turbine1 = eventBus.events.testIn(backgroundScope)
            val turbine2 = eventBus.events.testIn(backgroundScope)
            assertEquals(turbine1.awaitItem(), event)
            assertEquals(turbine2.awaitItem(), event)
        }
    }

    @Test
    fun `new subscribers should not receive past events`() = runTest {
        val event = TestEvent(1)

        launch {
            eventBus.send(event)
        }
        eventBus.events.test {
            assertEquals(awaitItem(), event)
        }
        // launch another coroutine
        assertFails {
            eventBus.events.test {
                assertEquals(awaitItem(), event)
            }
        }
    }

    @Test
    fun `multiple events are received in order by an active subscriber`() =
        runTest {
            val eventsToSend = listOf(TestEvent(1), TestEvent(2), TestEvent(3))

            eventsToSend.forEach { eventBus.send(it) }

            eventBus.events.test {
                eventsToSend.forEach {
                    assertEquals(it, awaitItem())
                }
            }
        }
}
