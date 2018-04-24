package br.com.disapps.data.events

import kotlinx.coroutines.experimental.channels.*
import kotlinx.coroutines.experimental.launch

class EventBus {
    val bus: BroadcastChannel<Any> = ConflatedBroadcastChannel()

    fun send(o: Any) {
        launch {
            bus.send(o)
        }
    }

    inline fun <reified T> asChannel(): ReceiveChannel<T> {
        return bus.openSubscription()
                .filter { it is T }.map { it as T }
    }
}