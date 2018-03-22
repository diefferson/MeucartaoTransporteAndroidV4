package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.domain.model.Card

/**
 * Created by dnso on 16/03/2018.
 */
object CardRequestMapper : Mapper<RequestCartao, Card>{

    override fun mapToEntity(data: Card): RequestCartao {
        val request = RequestCartao()
        request.codigo = data.code
        request.cpf = data.cpf
        request.tipoConsulta= "saldo"
        return request
    }

    override fun mapFromEntity(entity: RequestCartao): Card {
        return Card(code = entity.codigo, cpf = entity.cpf)
    }

    override fun mapToEntity(data: List<Card>): List<RequestCartao> {
        return data.map(this::mapToEntity)
    }

    override fun mapFromEntity(entity: List<RequestCartao>): List<Card> {
        return entity.map(this::mapFromEntity)
    }
}