package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Linha
import br.com.disapps.domain.model.Line

/**
 * Created by dnso on 16/03/2018.
 */
object LineEntityMapper : Mapper<Linha, Line>{

    override fun mapToEntity(data: Line): Linha {
        val entity = Linha()
        entity.codigo = data.code
        entity.nome = data.name
        entity.categoria = data.category
        entity.cor = data.color
        entity.favorito = data.favorite
        return entity
    }

    override fun mapFromEntity(entity: Linha): Line {
        return Line(
                code = entity.codigo,
                name = entity.nome,
                category = entity.categoria,
                color = entity.cor,
                favorite = entity.favorito
        )
    }

    override fun mapToEntity(data: List<Line>): List<Linha> {
        return data.map(this::mapToEntity)
    }

    override fun mapFromEntity(entity: List<Linha>): List<Line> {
        return entity.map(this::mapFromEntity)
    }
}
