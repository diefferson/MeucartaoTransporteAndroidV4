package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.Shape
import io.reactivex.Completable
import io.reactivex.Single

interface ShapesRepository {

    fun jsonShapes(city : City, downloadProgressListener: DownloadProgressListener) : Single<String>

    fun saveAllFromJson(json : String, city : City): Completable

    fun getShapes(codeLine : String) : Single<List<Shape>>
}