package br.com.disapps.domain.model

interface Event

enum class EventStatus{
    START, COMPLETE, ERROR
}

open class UpdateDataEvent(val status: EventStatus) : Event
class UpdateLinesEvent(status: EventStatus) : UpdateDataEvent(status)
class UpdateSchedulesEvent(status: EventStatus) : UpdateDataEvent(status)