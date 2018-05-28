package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.Shape

interface ShapesRepository {

    suspend fun saveAllFromJson( filePath:String, city : City,downloadProgressListener: DownloadProgressListener)

    suspend fun getShapes(codeLine : String) :List<Shape>
}