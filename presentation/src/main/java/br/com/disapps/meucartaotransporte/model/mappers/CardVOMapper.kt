package br.com.disapps.meucartaotransporte.model.mappers

import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO

object CardVOMapper : Mapper<CardVO, Card>{

    override fun mapToView(data: Card): CardVO {
       return  CardVO(
               code = data.code,
               cpf = data.cpf,
               name = data.name,
               type = data.type,
               status = data.status,
               balance = data.balance,
               balanceDate = data.balanceDate
       )
    }

    override fun mapFromView(entity: CardVO): Card {
        return  Card(
                code = entity.code,
                cpf = entity.cpf,
                name = entity.name,
                type = entity.type,
                status = entity.status,
                balance = entity.balance,
                balanceDate = entity.balanceDate
        )
    }

    override fun mapToView(data: List<Card>): List<CardVO> {
        return data.map(this::mapToView)
    }

    override fun mapFromView(entity: List<CardVO>): List<Card> {
        return entity.map(this::mapFromView)
    }
}