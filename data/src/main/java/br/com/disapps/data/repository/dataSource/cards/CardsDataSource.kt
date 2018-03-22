package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.repository.dataSource.DataSource
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
interface CardsDataSource : DataSource {
    /**
     * Save an {@link Cartao} in storage
     *
     * @param {@link Cartao} Cartao object to save on storage.
     */
    fun saveCard(cartao: Cartao): Observable<Boolean>

    /**
     * Save an {@link Cartao} in storage
     *
     * @param {@link Cartao} Cartao object to save on storage.
     */
    fun deleteCard(cartao: Cartao): Observable<Boolean>

    /**
     * Get an {@link Observable} which will emit a List of {@link Cartao}.
     */
    fun cards() : Observable<List<Cartao>>

    /**
     * Get an {@link Observable} which will emit a {@link Cartao}.
     *
     * @param RequestCartao The code of card used to retrieve card data.
     */
    fun card(requestCartao: RequestCartao) : Observable<Cartao?>

    /**
     * Get an {@link Observable} which will emit a {@link Cartao}.
     *
     * @param RequestCartao The code of card used to retrieve card data.
     */
    fun hasCard(cartao: Cartao) : Observable<Boolean>
}