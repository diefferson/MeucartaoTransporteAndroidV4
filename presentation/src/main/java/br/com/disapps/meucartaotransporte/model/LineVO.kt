package br.com.disapps.meucartaotransporte.model

data class LineVO(
        var code: String,
        var name: String,
        var category: String,
        var color: String,
        var favorite: Boolean,
        var searchableName :String
)
