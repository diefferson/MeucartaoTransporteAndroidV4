package br.com.disapps.data.database

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by diefferson.santos on 19/05/17.
 */

class MigrationData : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        if (oldVersion < 1) {
            val shapeSchema = realm.schema.get("Shape")
            shapeSchema!!.addField("numShape", String::class.java)
        }

        if (oldVersion < 2) {
            val pontoSchema = realm.schema.get("Ponto")
            pontoSchema!!.removeField("numPonto")
            pontoSchema.addField("numPonto", String::class.java)

            val horarioSchema = realm.schema.get("HorarioLinha")
            horarioSchema!!.removeField("numPonto")
            horarioSchema.addField("numPonto", String::class.java)
        }

        if (oldVersion < VERSION) {

            realm.delete("Ponto")
            realm.delete("Shape")

            val pontoSchema = realm.schema.get("Ponto")
            pontoSchema!!.addPrimaryKey("numPonto")

            val shapeSachema = realm.schema.get("Shape")
            shapeSachema!!.addPrimaryKey("numShape")

        }
    }

    companion object {

        const val VERSION = 4L
    }
}
