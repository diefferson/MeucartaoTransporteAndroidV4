package br.com.disapps.meucartaotransporte.model.mappers

interface Mapper<VO, BO> {
    fun mapToView(data: BO): VO
    fun mapFromView(entity: VO): BO
    fun mapToView(data: List<BO>): List<VO>
    fun mapFromView(entity: List<VO>): List<BO>
}