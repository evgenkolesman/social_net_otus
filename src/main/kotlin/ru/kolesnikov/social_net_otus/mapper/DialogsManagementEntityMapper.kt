package ru.kolesnikov.social_net_otus.mapper

import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy
import ru.kolesnikov.social_net_otus.entity.DialogsManagementEntity
import ru.kolesnikov.social_net_otus.model.DialogsManagementEntityDto

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface DialogsManagementEntityMapper {

    fun toEntity(dialogsManagementEntityDto: DialogsManagementEntityDto): DialogsManagementEntity

    fun toDto(dialogsManagementEntity: DialogsManagementEntity): DialogsManagementEntityDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun partialUpdate(
        dialogsManagementEntityDto: DialogsManagementEntityDto,
        @MappingTarget dialogsManagementEntity: DialogsManagementEntity,
    ): DialogsManagementEntity
}