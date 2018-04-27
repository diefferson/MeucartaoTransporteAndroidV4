package br.com.disapps.data.dataSource.cloud

import android.util.Log
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.CardsDataSource
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.entity.RetornoCartao
import br.com.disapps.data.entity.RetornoExtrato

/**
 * Created by dnso on 16/03/2018.
 */
class CloudCardsDataSource(private val restApi: RestApi) : CardsDataSource {

    override suspend fun card(requestCartao: RequestCartao): RetornoCartao? {
        val retornoCartao = restApi.saldoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).await()

        Log.i("teste", "teste")
        return retornoCartao

    }

    override suspend fun getExtract(requestCartao: RequestCartao): RetornoExtrato? {
        return restApi.extratoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).await()

    }

    override suspend fun hasCard(cartao: Cartao): Boolean {
        throw Throwable("not implemented, only local")
    }

    override suspend fun deleteCard( cartao: Cartao){
        throw Throwable("not implemented, only local")
    }

    override suspend fun saveCard(cartao: Cartao){
        throw Throwable("not implemented, only local")
    }

    override suspend fun cards(): List<Cartao> {
        throw Throwable("not implemented, only local")
    }
}