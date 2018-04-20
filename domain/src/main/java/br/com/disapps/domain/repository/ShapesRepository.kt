package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.Shape

interface ShapesRepository {

    suspend fun jsonShapes(city : City, downloadProgressListener: DownloadProgressListener) : String

    suspend fun saveAllFromJson(json : String, city : City)

    suspend fun getShapes(codeLine : String) :List<Shape>
}