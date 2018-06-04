package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.entity.Shape
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.model.City
import io.realm.Realm
import java.io.File
import java.io.FileInputStream

class LocalShapesDataSource(private val database: Database, private val preferences : Preferences) : ShapesDataSource {

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private val CLAZZ = Shape::class.java
    }

    override suspend fun saveAllFromJson(city: City, filePath: String) {
        val realm = database.getDatabase() as Realm
        val fis = FileInputStream(File(filePath))
        realm.beginTransaction()
        realm.createOrUpdateAllFromJson(CLAZZ, fis)
        realm.commitTransaction()
        if(city == City.CWB){
            preferences.setCwbShapesDate()
        }else{
            preferences.setMetShapesDate()
        }
        realm.close()
        deleteFromCache(filePath)
    }

    override suspend fun getShapes(codeLine: String): List<Shape> {
        val realm = database.getDatabase() as Realm
        val shapes = realm.copyFromRealm(realm.where(CLAZZ)
                                            .equalTo(CODE_LINE, codeLine)
                                            .findAll())
        realm.close()
        return shapes
    }
}