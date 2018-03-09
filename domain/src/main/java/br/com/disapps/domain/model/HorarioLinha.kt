package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */

data class HorarioLinha (
    var codigoLinha: String,
    var dia: Int = 0,
    var ponto: String,
    var numPonto: String,
    var horarios: List<Horario>,
    var proximosHorarios: List<Horario>
)
