package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Line
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting {@link Line} related data.
 */
interface LinesRepository{

    /**
     * Save an {@link Line} in storage
     *
     * @param {@link Line} Line object to save on storage.
     */
    fun saveLine(line : Line)

    /**
     * Save all {@link Line} in storage
     *
     * @param {@link String} String with json object to save on storage.
     */
    fun saveAllFromJson(json : String)

    /**
     * Get an {@link Observable} which will emit a List of {@link Line}.
     */
    fun jsonLines() : Observable<String>

    /**
     * Get an {@link Observable} which will emit a List of {@link Line}.
     */
    fun lines() : Observable<List<Line>>

    /**
     * Get an {@link Observable} which will emit a {@link Line}.
     *
     * @param code The code of line used to retrieve line data.
     */
    fun line(line: Line) : Observable<Line>

}