package br.com.disapps.meucartaotransporte.base.model

enum class NotificationChannels(var value: String){
    DEFAULT("DEFAULT"),
    UPDATING_DATA("UPDATING_DATA"),
    NEWS("NEWS");

    override fun toString(): String {
        return value
    }
}