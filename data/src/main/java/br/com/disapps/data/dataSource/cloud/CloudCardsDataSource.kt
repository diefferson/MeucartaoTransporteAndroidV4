package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.CardsDataSource
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.exception.cardOrError
import br.com.disapps.data.exception.extractOrError

/**
 * Created by dnso on 16/03/2018.
 */
class CloudCardsDataSource(private val restApi: RestApi) : CardsDataSource {

    override suspend fun card(requestCartao: RequestCartao): Cartao? {
        return cardOrError( restApi.saldoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).await())
    }

    override suspend fun getExtract(requestCartao: RequestCartao): List<Extrato>? {
        return extractOrError(restApi.extratoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).await())
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

    override suspend fun updateCard(cartao: Cartao){
        throw Throwable("not implemented, only local")
    }
}