package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */
data class Shape (
    var code: String,
    var lineCode: String,
    var coordinates: List<Coordinate>
)