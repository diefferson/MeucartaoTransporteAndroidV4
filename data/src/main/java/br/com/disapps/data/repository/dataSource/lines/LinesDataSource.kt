package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.repository.dataSource.DataSource
import io.reactivex.Observable

interface LinesDataSource : DataSource {

    fun saveLine(linha : Linha): Observable<Boolean>

    fun saveAllFromJson(json : String): Observable<Boolean>

    fun lines() : Observable<List<Linha>>

    fun jsonLines() : Observable<String>

    fun line(linha: Linha) : Observable<Linha>

    fun updateLine(linha: Linha):Observable<Boolean>
}