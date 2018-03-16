package br.com.disapps.data.entity.mapper

import br.com.disapps.data.entity.Cartao
import br.com.disapps.domain.model.Card


/**
 * Created by dnso on 15/03/2018.
 */
class CardEntityMapper : Mapper<Cartao, Card>{

    override fun mapFromEntity(cartao: Cartao): Card {
        return Card(
            code = cartao.codigo,
            cpf = cartao.cpf,
            name = cartao.nome,
            type = cartao.tipo,
            status = cartao.estado,
            balance = cartao.saldo,
            balanceDate = cartao.dataSaldo,
            message = cartao.mensagem
        )
    }

    override fun mapToEntity(card: Card): Cartao {
        val cartao = Cartao()
        cartao.codigo = card.code
        cartao.cpf = card.cpf
        cartao.nome = card.name
        cartao.tipo = card.type
        cartao.estado = card.status
        cartao.saldo = card.balance
        cartao.dataSaldo = card.balanceDate
        cartao.mensagem = card.message
        return cartao
    }

    override fun mapFromEntity(entity: List<Cartao>): List<Card> {
       return entity.map(this::mapFromEntity)
    }

    override fun mapToEntity(data: List<Card>): List<Cartao> {
        return data.map(this::mapToEntity)
    }
}

