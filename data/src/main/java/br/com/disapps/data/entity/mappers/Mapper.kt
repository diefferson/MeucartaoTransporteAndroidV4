package br.com.disapps.data.entity.mappers

/**
 * Created by dnso on 15/03/2018.
 */
interface Mapper<DTO, BO> {
    fun mapToEntity(data: BO): DTO
    fun mapFromEntity(entity: DTO): BO
    fun mapToEntity(data: List<BO>): List<DTO>
    fun mapFromEntity(entity: List<DTO>): List<BO>
}