package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */
data class BusStop(
    var code: String,
    var name: String,
    var lineCode: String,
    var latitude: String,
    var longitude: String,
    var sequence: Int,
    var direction: String,
    var type: String
)