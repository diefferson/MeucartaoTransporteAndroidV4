package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Cartao
import br.com.disapps.domain.model.Card


/**
 * Created by dnso on 15/03/2018.
 */
object CardEntityMapper : Mapper<Cartao, Card>{

    override fun mapFromEntity(entity: Cartao): Card {
        return Card(
            code = entity.codigo,
            cpf = entity.cpf,
            name = entity.nome,
            type = entity.tipo,
            status = entity.estado,
            balance = entity.saldo,
            balanceDate = entity.dataSaldo,
            message = entity.mensagem
        )
    }

    override fun mapToEntity(data: Card): Cartao {
        val cartao = Cartao()
        cartao.codigo = data.code
        cartao.cpf = data.cpf
        cartao.nome = data.name
        cartao.tipo = data.type
        cartao.estado = data.status
        cartao.saldo = data.balance
        cartao.dataSaldo = data.balanceDate
        cartao.mensagem = data.message
        return cartao
    }

    override fun mapFromEntity(entity: List<Cartao>): List<Card> {
       return entity.map(this::mapFromEntity)
    }

    override fun mapToEntity(data: List<Card>): List<Cartao> {
        return data.map(this::mapToEntity)
    }
}

