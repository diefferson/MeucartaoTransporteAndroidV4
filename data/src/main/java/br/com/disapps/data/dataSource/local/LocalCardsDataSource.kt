package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.CardsDataSource
import br.com.disapps.data.entity.*
import br.com.disapps.data.storage.database.Database
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalCardsDataSource(private val database: Database) : CardsDataSource {

    companion object {
        private const val CODE = "codigo"
        private val CLAZZ = Cartao::class.java
    }

    override suspend fun saveCard(cartao: Cartao){
        val realm = database.getDatabase() as Realm
        realm.beginTransaction()
        realm.copyToRealm(cartao)
        realm.commitTransaction()
        realm.close()
    }

    override suspend fun updateCard(cartao: Cartao) {
        if(hasCard(cartao)){

            val completeCard = card(RequestCartao().apply { codigo = cartao.codigo })!!
            completeCard.apply {
                data_saldo = cartao.data_saldo
                saldo = cartao.saldo
            }

            val realm = database.getDatabase() as Realm
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(completeCard)
            realm.commitTransaction()
            realm.close()
        }
    }

    override suspend fun deleteCard( cartao: Cartao){
        val realm = database.getDatabase() as Realm
        realm.beginTransaction()
        val card = realm.where(CLAZZ)
                .equalTo(CODE, cartao.codigo)
                .findFirst()

        card?.deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    override suspend fun cards(): List<Cartao> {
        val realm = database.getDatabase() as Realm
        val cards = realm.copyFromRealm(realm.where(CLAZZ).findAll())
        realm.close()
        return cards
    }

    override suspend fun card(requestCartao: RequestCartao): Cartao? {
        val realm = database.getDatabase() as Realm
        val card = realm.copyFromRealm(realm.where(CLAZZ)
                        .equalTo(CODE, requestCartao.codigo)
                        .findAll())[0]
        realm.close()
        return card
    }

    override suspend fun hasCard(cartao: Cartao): Boolean {
        val realm = database.getDatabase() as Realm
        val hasCards = realm.where(CLAZZ)
                            .equalTo(CODE, cartao.codigo)
                            .findAll().size > 0
        realm.close()
        return hasCards
    }

    override suspend fun getExtract(requestCartao: RequestCartao): List<Extrato>? {
         throw Throwable("not implemented,  cloud only")
    }
}