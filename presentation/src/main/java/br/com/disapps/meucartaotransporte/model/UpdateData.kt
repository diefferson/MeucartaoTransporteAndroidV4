package br.com.disapps.meucartaotransporte.model

enum class UpdateData(private var data : Int){
    LINES(0),
    SCHEDULES(1),
    CWB_ITINERARIES (2),
    CWB_SHAPES (3),
    MET_ITINERARIES (4),
    MET_SHAPES (5);

    fun toInt(): Int{
        return data
    }
}