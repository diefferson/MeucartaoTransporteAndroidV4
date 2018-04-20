package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.CardsDataSource
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.storage.database.Database
import br.com.disapps.domain.interactor.base.CompletableCallback
import io.reactivex.Single
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalCardsDataSource(private val database: Database) : CardsDataSource {

    companion object {
        private const val CODE = "codigo"
        private val CLAZZ = Cartao::class.java
    }

    override fun saveCard(cartao: Cartao, callback: CompletableCallback) {
        val realm = database.getDatabase() as Realm
        return try {
            realm.beginTransaction()
            realm.copyToRealm(cartao)
            realm.commitTransaction()
            callback.onComplete()
        }catch (ec: Exception){
            callback.onError(ec)
        }finally {
            realm.close()
        }
    }

    override fun deleteCard( cartao: Cartao, callback: CompletableCallback){
        val realm = database.getDatabase() as Realm
        return try {
            realm.beginTransaction()
            val card = realm.where(CLAZZ)
                    .equalTo(CODE, cartao.codigo)
                    .findFirst()

            card?.deleteFromRealm()
            realm.commitTransaction()
            callback.onComplete()
        }catch (ec: Exception){
            callback.onError(ec)
        }finally {
            realm.close()
        }
    }

    override fun cards(): Single<List<Cartao>> {
        val realm = database.getDatabase() as Realm
        val cards = realm.copyFromRealm(realm.where(CLAZZ).findAll())
        realm.close()
        return Single.just(cards)
    }

    override fun card(requestCartao: RequestCartao): Single<Cartao?> {
        val realm = database.getDatabase() as Realm
        val card = realm.where(CLAZZ)
                        .equalTo(CODE, requestCartao.codigo)
                        .findFirst()

        realm.close()
        return Single.just(card)
    }

    override fun hasCard(cartao: Cartao): Single<Boolean> {
        val realm = database.getDatabase() as Realm
        val hasCards = realm.where(CLAZZ)
                            .equalTo(CODE, cartao.codigo)
                            .findAll().size > 0
        realm.close()
        return Single.just(hasCards)
    }



    override fun getExtract(requestCartao: RequestCartao): Single<List<Extrato>> {
        return Single.error<List<Extrato>>(Throwable("not implemented,  cloud only"))
    }
}