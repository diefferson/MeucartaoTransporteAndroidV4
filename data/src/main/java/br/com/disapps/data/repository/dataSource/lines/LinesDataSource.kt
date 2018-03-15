package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.repository.dataSource.DataSource
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
interface LinesDataSource : DataSource {

    /**
     * Save an {@link Linha} in storage
     *
     * @param {@link Linha} Linha object to save on storage.
     */
    fun saveLine(linha : Linha)

    /**
     * Save all {@link Linha} in storage
     *
     * @param {@link String} String with json object to save on storage.
     */
    fun saveAllFromJson(json : String)

    /**
     * Get an {@link Observable} which will emit a List of {@link Linha}.
     */
    fun lines() : Observable<List<Linha>>

    /**
     * Get an {@link Observable} which will emit a List of {@link Line}.
     */
    fun jsonLines() : Observable<String>

    /**
     * Get an {@link Observable} which will emit a {@link Linha}.
     *
     * @param code The code of line used to retrieve line data.
     */
    fun line() : Observable<Linha>
}