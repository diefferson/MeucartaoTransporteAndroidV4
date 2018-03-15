package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.storage.database.Database
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class LocalCardsDataSource(private var database: Database) : CardsDataSource {

    override fun saveCard(cartao: Cartao) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cards(): Observable<List<Cartao>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun card(code: String): Observable<Cartao> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}