package br.com.disapps.data.storage.database

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by diefferson.santos on 19/05/17.
 */

class MigrationData : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        if (oldVersion < 1) {
            val shapeSchema = realm.schema.get("Shape")
            shapeSchema?.addField("numShape", String::class.java)
        }

        if (oldVersion < 2) {
            val pontoSchema = realm.schema.get("Ponto")
            pontoSchema?.removeField("numPonto")
            pontoSchema?.addField("numPonto", String::class.java)

            val horarioSchema = realm.schema.get("HorarioLinha")
            horarioSchema?.removeField("numPonto")
            horarioSchema?.addField("numPonto", String::class.java)
        }

        if (oldVersion < 3) {

            realm.delete("Ponto")
            realm.delete("Shape")

            val pontoSchema = realm.schema.get("Ponto")
            pontoSchema?.addPrimaryKey("numPonto")

            val shapeSachema = realm.schema.get("Shape")
            shapeSachema?.addPrimaryKey("numShape")

        }

        if(oldVersion < VERSION){
            val cartaoSchema = realm.schema.get("Cartao")
            cartaoSchema?.removePrimaryKey()
            cartaoSchema?.setRequired("codigo",true)
            cartaoSchema?.addPrimaryKey("codigo")
            cartaoSchema?.setRequired("cpf",true)
            cartaoSchema?.setRequired("nome",true)
            cartaoSchema?.setRequired("tipo",true)
            cartaoSchema?.setRequired("estado",true)
            cartaoSchema?.setRequired("data_saldo",true)

            val coordenadaSchema = realm.schema.get("Coordenada")
            coordenadaSchema?.setRequired("latitude", true)
            coordenadaSchema?.setRequired("longitude", true)

            val horarioSchema = realm.schema.get("Horario")
            horarioSchema?.setRequired("hora", true)
            horarioSchema?.setRequired("tabelaHoraria", true)
            horarioSchema?.setRequired("adapt", true)

            val horarioLinhaSchema = realm.schema.get("HorarioLinha")
            horarioLinhaSchema?.setRequired("numPonto", true)
            horarioLinhaSchema?.setRequired("codigoLinha", true)
            horarioLinhaSchema?.setRequired("ponto", true)

            val linhaSchema = realm.schema.get("Linha")
            linhaSchema?.removePrimaryKey()
            linhaSchema?.setRequired("codigo",true)
            linhaSchema?.addPrimaryKey("codigo")
            linhaSchema?.setRequired("cor", true)
            linhaSchema?.setRequired("nome", true)
            linhaSchema?.setRequired("categoria", true)

            val pontoSchema = realm.schema.get("Ponto")
            pontoSchema?.removePrimaryKey()
            pontoSchema?.setRequired("numPonto",true)
            pontoSchema?.addPrimaryKey("numPonto")
            pontoSchema?.setRequired("tipo", true)
            pontoSchema?.setRequired("nomePonto", true)
            pontoSchema?.setRequired("codigoLinha", true)
            pontoSchema?.setRequired("latitude", true)
            pontoSchema?.setRequired("longitude", true)
            pontoSchema?.setRequired("sentido", true)


            val shapeSchema = realm.schema.get("Shape")
            shapeSchema?.removePrimaryKey()
            shapeSchema?.setRequired("numShape",true)
            shapeSchema?.addPrimaryKey("numShape")
            shapeSchema?.setRequired("codigoLinha", true)

        }
    }

    companion object {

        const val VERSION = 5L
    }
}
