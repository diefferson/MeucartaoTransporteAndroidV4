package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.storage.database.Database
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalCardsDataSource(private var database: Database) : CardsDataSource {

    override fun saveCard(cartao: Cartao): Observable<Boolean> {
        val realm = database.getDatabase() as Realm
        return try {

            realm.beginTransaction()
            realm.copyToRealm(cartao)
            realm.commitTransaction()
            Observable.just(true)
        }catch (ec: Exception){
            Observable.just(false)
        }finally {
            realm.close()
        }
    }

    override fun cards(): Observable<List<Cartao>> {
        val realm = database.getDatabase() as Realm
        val cards = realm.copyFromRealm(realm.where(Cartao::class.java).findAll().toList())
        realm.close()
        return Observable.just(cards)
    }

    override fun card(requestCartao: RequestCartao): Observable<Cartao?> {
        val realm = database.getDatabase() as Realm
        val card = realm.where(Cartao::class.java).equalTo("codigo", requestCartao.codigo).findFirst()
        realm.close()
        return Observable.just(card)
    }

    override fun hasCard(cartao: Cartao): Observable<Boolean> {
        val realm = database.getDatabase() as Realm
        return if (realm.where(Cartao::class.java).equalTo("codigo", cartao.codigo).findAll().size > 0) {
            realm.close()
            Observable.just(true)
        }else{
            realm.close()
            Observable.just(false)
        }
    }

    override fun deleteCard(cartao: Cartao): Observable<Boolean> {
        val realm = database.getDatabase() as Realm
        return try {
            realm.beginTransaction()
            val card = realm.where(Cartao::class.java).equalTo("codigo", cartao.codigo).findFirst()
            card?.deleteFromRealm()
            realm.commitTransaction()
            Observable.just(true)
        }catch (ec: Exception){
            Observable.just(false)
        }finally {
            realm.close()
        }
    }
}