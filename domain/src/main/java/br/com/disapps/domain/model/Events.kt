package br.com.disapps.domain.model

interface Event

enum class EventStatus{
    START, COMPLETE, ERROR
}

open class UpdateDataEvent(val status: EventStatus, val message : String) : Event
class UpdateLinesEvent(status: EventStatus, message : String) : UpdateDataEvent(status, message)
class UpdateSchedulesEvent(status: EventStatus,message : String) : UpdateDataEvent(status, message)
class UpdateCwbShapesEvent(status: EventStatus,message : String) : UpdateDataEvent(status, message)
class UpdateCwbItinerariesEvent(status: EventStatus, message : String): UpdateDataEvent(status, message)
class UpdateMetShapesEvent(status: EventStatus,message : String) : UpdateDataEvent(status, message)
class UpdateMetItinerariesEvent( status: EventStatus, message : String) : UpdateDataEvent(status, message)