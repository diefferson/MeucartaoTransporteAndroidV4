package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */
data class Line(
    var code: String,
    var name: String,
    var category: String,
    var color: String,
    var favorite: Int = 0
)
