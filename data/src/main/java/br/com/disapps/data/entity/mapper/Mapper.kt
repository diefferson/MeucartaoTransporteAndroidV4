package br.com.disapps.data.entity.mapper

/**
 * Created by dnso on 15/03/2018.
 */
interface Mapper<DTO, BO> {
    fun mapToEntity(data: DTO): BO
    fun mapFromEntity(entity: BO): DTO
}