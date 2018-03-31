package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Line
import io.reactivex.Observable


interface LinesRepository{

    fun saveLine(line : Line): Observable<Boolean>

    fun saveAllFromJson(json : String): Observable<Boolean>

    fun jsonLines() : Observable<String>

    fun lines() : Observable<List<Line>>

    fun line(line: Line) : Observable<Line>

    fun updateLine(line : Line): Observable<Boolean>

}