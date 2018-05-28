package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Shape
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City

interface ShapesDataSource : DataSource{

    suspend fun saveAllFromJson(filePath:String, city : City,  downloadProgressListener: DownloadProgressListener)

    suspend fun getShapes(codeLine : String) : List<Shape>
}