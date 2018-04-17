package br.com.disapps.domain.model

enum class City(val isMetropolitan : String){
    CWB("NAO"),
    MET("SIM");

    override fun toString(): String {
        return isMetropolitan
    }
}