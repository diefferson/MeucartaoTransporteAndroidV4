package br.com.disapps.data.events

import kotlinx.coroutines.experimental.channels.*
import kotlinx.coroutines.experimental.launch

class EventBus constructor() {
    val bus: BroadcastChannel<Any> = ConflatedBroadcastChannel()

    fun send(o: Any) {
        launch {
            bus.send(o)
        }
    }

    fun <T> asChannel(klazz: Class<T>): ReceiveChannel<T> {
        return bus.openSubscription().filter {
            it::class.java == klazz
        }.map {
            it as T
        }
    }
}