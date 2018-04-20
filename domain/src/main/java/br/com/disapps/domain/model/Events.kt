package br.com.disapps.domain.model

interface Event

class UpdateLinesEventComplete : Event
class UpdateLinesEventError : Event
class UpdateSchedulesEventComplete : Event
class UpdateSchedulesEventError : Event